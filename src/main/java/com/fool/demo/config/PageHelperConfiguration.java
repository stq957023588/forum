package com.fool.demo.config;

import com.fool.demo.interceptor.DataRuleMybatisInterceptor;
import com.github.pagehelper.PageInterceptor;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/12 16:04
 */
@Configuration
@ConditionalOnClass(SqlSessionFactory.class)
@EnableConfigurationProperties(PageHelperProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class PageHelperConfiguration implements InitializingBean {
    private final List<SqlSessionFactory> sqlSessionFactoryList;

    private final PageHelperProperties properties;

    public PageHelperConfiguration(List<SqlSessionFactory> sqlSessionFactoryList, PageHelperProperties properties) {
        this.sqlSessionFactoryList = sqlSessionFactoryList;
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PageInterceptor pageInterceptor = new PageInterceptor();
        DataRuleMybatisInterceptor dataRuleMybatisInterceptor = new DataRuleMybatisInterceptor();
        pageInterceptor.setProperties(this.properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            if (!containsInterceptor(configuration, pageInterceptor)) {
                configuration.addInterceptor(pageInterceptor);
            }
            if (!containsInterceptor(configuration, dataRuleMybatisInterceptor)) {
                configuration.addInterceptor(dataRuleMybatisInterceptor);
            }
        }
    }

    /**
     * 是否已经存在相同的拦截器
     *
     * @param configuration
     * @param interceptor
     * @return
     */
    private boolean containsInterceptor(org.apache.ibatis.session.Configuration configuration, Interceptor interceptor) {
        try {
            // getInterceptors since 3.2.2
            return configuration.getInterceptors().contains(interceptor);
        } catch (Exception e) {
            return false;
        }
    }

}
