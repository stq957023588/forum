package com.fool.demo.config;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fool.demo.customize.CustomizeAccessDecisionManager;
import com.fool.demo.customize.CustomizeFilterInvocationSecurityMetadataSource;
import com.fool.demo.customize.CustomizeUsernamePasswordAuthenticationFilter;
import com.fool.demo.entity.CustomizeUser;
import com.fool.demo.entity.DefaultResult;
import com.fool.demo.filter.JwtAuthenticationFilter;
import com.fool.demo.property.JwtProperty;
import com.fool.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2021/8/13 16:36
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    private JwtProperty jwtProperty;

    private CustomizeAccessDecisionManager accessDecisionManager;

    private CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setJwtProperty(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    @Autowired
    public void setAccessDecisionManager(CustomizeAccessDecisionManager accessDecisionManager) {
        this.accessDecisionManager = accessDecisionManager;
    }

    @Autowired
    public void setSecurityMetadataSource(CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 将security中加密方式注入到spring容器中
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 将账号密码设置在数据库当中
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //将UserDetailsService放到容器中
                .userDetailsService(userService)
                //加密方式放入
                .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);

        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //决策管理器
                        o.setAccessDecisionManager(accessDecisionManager);
                        //安全元数据源
                        o.setSecurityMetadataSource(securityMetadataSource);
                        return o;
                    }
                })
                .and()
                //登出
                .logout()
                //允许所有用户
                .permitAll()
                //登出成功处理逻辑
                .logoutSuccessHandler(logoutSuccessHandler())
                //登出之后删除cookie
                .deleteCookies("JSESSIONID")
                // 登入,使用自定义登录
                // .and().formLogin()
                // //允许所有用户
                // .permitAll()
                // //登录成功处理逻辑
                // .successHandler(authenticationSuccessHandler())
                // //登录失败处理逻辑
                // .failureHandler(authenticationFailureHandler())
                //异常处理(权限拒绝、登录失效等)
                .and().exceptionHandling()
                //权限拒绝处理逻辑
                .accessDeniedHandler(accessDeniedHandler())
                //匿名用户访问无权限资源时的异常处理
                .authenticationEntryPoint(authenticationEntryPoint())
                //会话管理
                .and()
                // 取消Session,使用JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // .sessionManagement()
        // 同一账号同时登录最大用户数
        // .maximumSessions(1)
        // 会话失效(账号被挤下线)处理逻辑
        // .expiredSessionStrategy(sessionInformationExpiredStrategy());
        // 将自定义的Token处理过滤器添加到UsernamePasswordAuthenticationFilter之前
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProperty);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 自定义账号密码验证登录处理
        CustomizeUsernamePasswordAuthenticationFilter customizeUsernamePasswordAuthenticationFilter = new CustomizeUsernamePasswordAuthenticationFilter();
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        http.addFilterAt(customizeUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * Session失效调用
     */
    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return sessionInformationExpiredEvent -> {
            DefaultResult<Object> result = DefaultResult.beOffline();
            HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
        };
    }

    /**
     * 访问被拒
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            DefaultResult<?> result = DefaultResult.accessDenied();
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));

        };
    }

    /**
     * 未登录
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) -> {
            DefaultResult<?> result = DefaultResult.notLoggedIn();
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
        };
    }


    /**
     * 登录成功
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            CustomizeUser userDetails = (CustomizeUser) authentication.getPrincipal();

            long current = System.currentTimeMillis();

            String token = JWT.create().withIssuer(jwtProperty.getIssuer())
                    .withIssuedAt(new Date(current))
                    .withExpiresAt(new Date(current + jwtProperty.getExpiration()))
                    .withClaim("id", userDetails.getId())
                    .withClaim("name", userDetails.getName())
                    .withClaim("email", userDetails.getUsername())
                    .withClaim("avatar", userDetails.getAvatar())
                    .withArrayClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).toArray(new String[]{}))
                    .sign(Algorithm.HMAC256(jwtProperty.getSecret()));
            DefaultResult<Object> result = DefaultResult.success("登陆成功", token);
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
        };
    }

    /**
     * 登录失败
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            DefaultResult<Object> result = DefaultResult.authFailed(e.getMessage());
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
        };
    }

    /**
     * 登出
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            DefaultResult<Object> result = DefaultResult.success("登出成功");
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
        };
    }

}
