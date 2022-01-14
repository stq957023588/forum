package com.fool.demo.interceptor;

import com.fool.demo.annotation.DataAuthority;
import com.fool.demo.utils.DataRuleUtils;
import com.fool.demo.utils.ExtraCondition;
import com.github.pagehelper.util.MetaObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fool
 * @date 2022/1/7 17:11
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Slf4j
public class DataRuleMybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }
        Object[] paramArr = getParamArr(parameter);

        String msId = ms.getId();
        String clazzName = msId.substring(0, msId.lastIndexOf('.'));
        String method = msId.substring(msId.lastIndexOf('.') + 1);

        Class<?> aClass = Class.forName(clazzName);

        Method methodObj = getMethod(aClass, method, paramArr);
        DataAuthority annotation = methodObj.getAnnotation(DataAuthority.class);
        if (annotation == null) {
            return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        }

        log.info("Mapper id:{}", ms.getId());
        List<ExtraCondition> dataRules = DataRuleUtils.getDataRules();

        parameter = processParameterObject(ms, parameter, boundSql, cacheKey, dataRules);
        String dataRuleSql = getDataRuleSql(boundSql, dataRules);
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), dataRuleSql, boundSql.getParameterMappings(), parameter);
        dataRules.clear();

        //注：下面的方法可以根据自己的逻辑调用多次，在分页插件中，count 和 page 各调用了一次
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, newBoundSql);
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    private static final Map<Class<?>, Map<String, List<List<Class<?>>>>> MAPPER_CACHE = new ConcurrentHashMap<>();


    /**
     * 获取 mapper 相应 Method 反射类
     */
    private Method getMethod(Class<?> clazz, String mapperMethod, Object[] paramArr) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        // 1、查 mapper 接口缓存,没有缓存, 就进行缓存
        if (!MAPPER_CACHE.containsKey(clazz)) {
            cacheMapper(clazz);
        }
        // 2、返回相应 method
        A:
        for (List<Class<?>> paramList : MAPPER_CACHE.get(clazz).get(mapperMethod)) {
            if (!paramList.isEmpty()) {
                for (int i = 0; i < paramArr.length; i++) { // 比较参数列表class
                    if (paramArr[i] != null) {
                        if (!compareClass(paramList.get(i), paramArr[i].getClass())) {
                            continue A;
                        }
                    }
                }
                return clazz.getMethod(mapperMethod, paramList.toArray(new Class[0]));
            }
        }
        return clazz.getMethod(mapperMethod); // 返回无参方法
    }

    /**
     * 对 mapper 方法字段进行缓存
     */
    private void cacheMapper(Class<?> clazz) {
        Map<String, List<List<Class<?>>>> methodMap = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            List<List<Class<?>>> paramLists = methodMap.containsKey(method.getName()) ? methodMap.get(method.getName()) : new ArrayList<>();
            List<Class<?>> paramClass = new ArrayList<>();
            for (Type type : method.getParameterTypes()) {
                paramClass.add((Class<?>) type);
            }
            paramLists.add(paramClass);
            methodMap.put(method.getName(), paramLists);
        }
        MAPPER_CACHE.put(clazz, methodMap);
    }

    /**
     * class 比较
     */
    private boolean compareClass(Class<?> returnType, Class<?> paramType) throws NoSuchFieldException, IllegalAccessException {
        if (returnType == paramType) {
            return true;
        } else if (returnType.isAssignableFrom(paramType)) { // 判断 paramType 是否为 returnType 子类或者实现类
            return true;
        }
        // 基本数据类型判断
        else if (returnType.isPrimitive()) { // paramType为包装类
            return returnType == paramType.getField("TYPE").get(null);
        } else if (paramType.isPrimitive()) { // returnType为包装类
            return paramType == returnType.getField("TYPE").get(null);
        }
        return false;
    }

    private Object[] getParamArr(Object parameter) {
        Object[] paramArr = null;
        // mapper 接口中使用的是 paramMap, 传多个参数
        if (parameter instanceof MapperMethod.ParamMap) {
            Map<String, ?> map = ((Map<String, ?>) parameter);
            if (!map.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                // 初始化 param_arr
                int size = map.size() >> 1;
                paramArr = new Object[size];
                for (int i = 1; i <= size; i++) {
                    // mapper 接口中使用 param0 ~ paramN 命名参数
                    paramArr[i - 1] = map.get(builder.append("param").append(i).toString());
                    builder.setLength(0);
                }
            }
        } else if (parameter != null) {
            paramArr = new Object[1];
            paramArr[0] = parameter;
        }
        return paramArr;
    }

    public String getDataRuleSql(BoundSql boundSql, List<ExtraCondition> extraConditions) {
        String sql = boundSql.getSql();
        if (extraConditions == null || extraConditions.isEmpty()) {
            return sql;
        }
        int l = extraConditions.stream().map(ExtraCondition::getKey).map(String::length).mapToInt(i -> i + 11).sum() + 6;

        StringBuilder sqlBuilder = new StringBuilder(sql.length() + l);
        sqlBuilder.append(sql);
        for (ExtraCondition extraCondition : extraConditions) {
            sqlBuilder.append("\n and ").append(extraCondition.getKey()).append(" = ?");
        }
        return sqlBuilder.toString();
    }

    public Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql, CacheKey cacheKey, List<ExtraCondition> extraConditions) {
        if (extraConditions == null || extraConditions.isEmpty()) {
            return parameterObject;
        }

        Map<String, Object> paramMap = new HashMap<>();
        if (parameterObject instanceof Map) {
            paramMap.putAll((Map<String, Object>) parameterObject);
        } else if (parameterObject != null) {
            //动态sql时的判断条件不会出现在ParameterMapping中，但是必须有，所以这里需要收集所有的getter属性
            //TypeHandlerRegistry可以直接处理的会作为一个直接使用的对象进行处理
            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass());
            MetaObject metaObject = MetaObjectUtil.forObject(parameterObject);
            //需要针对注解形式的MyProviderSqlSource保存原值
            if (!hasTypeHandler) {
                for (String name : metaObject.getGetterNames()) {
                    paramMap.put(name, metaObject.getValue(name));
                }
            }
        }

        for (ExtraCondition extraCondition : extraConditions) {
            paramMap.put(extraCondition.getKey(), extraCondition.getValue());
        }
        // Map<String, Object> collect = extraParameters.stream().collect(Collectors.toMap(ExtraParameter::getKey, ExtraParameter::getValue, (v1, v2) -> v1));

        for (ExtraCondition extraCondition : extraConditions) {
            cacheKey.update(extraCondition.getValue());
        }

        if (boundSql.getParameterMappings() != null) {
            List<ParameterMapping> newParameterMappings = new ArrayList<>(boundSql.getParameterMappings());

            for (ExtraCondition extraCondition : extraConditions) {
                newParameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), extraCondition.getKey(), Optional.ofNullable(extraCondition.getValueType()).orElse(String.class)).build());
            }
            MetaObject metaObject = MetaObjectUtil.forObject(boundSql);
            metaObject.setValue("parameterMappings", newParameterMappings);
        }

        return paramMap;
    }

}
