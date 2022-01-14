package com.fool.demo.filter;

import com.fool.demo.entity.DataRuleDTO;
import com.fool.demo.entity.UserDTO;
import com.fool.demo.mapstruct.DataRuleConvertor;
import com.fool.demo.service.DataRuleService;
import com.fool.demo.utils.DataRuleUtils;
import com.fool.demo.utils.ExtraCondition;
import com.fool.demo.utils.SpringContextUtils;
import com.fool.demo.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 普通的Filter会在Spring Security Filter 之后执行
 *
 * @author fool
 * @date 2022/1/12 9:46
 */
@Slf4j
@Component
@Order(1)
public class DataRuleFilter implements Filter {

    private final DataRuleService dataRuleService;

    public DataRuleFilter(DataRuleService dataRuleService) {
        this.dataRuleService = dataRuleService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            UserDTO currentUser = UserUtils.getCurrentUser();
            List<String> roles = currentUser.getRoles();
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String requestUrl = httpServletRequest.getRequestURI();
            String method = httpServletRequest.getMethod();

            List<DataRuleDTO> dataRules = dataRuleService.getDataRuleByRolesAndAuthority(roles, requestUrl, method);

            List<ExtraCondition> extraConditions = dataRules.stream().map(DataRuleConvertor.INSTANCE::toExtraCondition).collect(Collectors.toList());
            DataRuleUtils.setDataRules(extraConditions);
        } catch (Exception e) {
            HandlerExceptionResolver bean = SpringContextUtils.getBean(HandlerExceptionResolver.class, "handlerExceptionResolver");
            bean.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, e);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
