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
     * ???security????????????????????????spring?????????
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * ???????????????????????????????????????
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //???UserDetailsService???????????????
                .userDetailsService(userService)
                //??????????????????
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
                        //???????????????
                        o.setAccessDecisionManager(accessDecisionManager);
                        //??????????????????
                        o.setSecurityMetadataSource(securityMetadataSource);
                        return o;
                    }
                })
                .and()
                //??????
                .logout()
                //??????????????????
                .permitAll()
                //????????????????????????
                .logoutSuccessHandler(logoutSuccessHandler())
                //??????????????????cookie
                .deleteCookies("JSESSIONID")
                // ??????,?????????????????????
                // .and().formLogin()
                // //??????????????????
                // .permitAll()
                // //????????????????????????
                // .successHandler(authenticationSuccessHandler())
                // //????????????????????????
                // .failureHandler(authenticationFailureHandler())
                //????????????(??????????????????????????????)
                .and().exceptionHandling()
                //????????????????????????
                .accessDeniedHandler(accessDeniedHandler())
                //???????????????????????????????????????????????????
                .authenticationEntryPoint(authenticationEntryPoint())
                //????????????
                .and()
                // ??????Session,??????JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // .sessionManagement()
        // ???????????????????????????????????????
        // .maximumSessions(1)
        // ????????????(??????????????????)????????????
        // .expiredSessionStrategy(sessionInformationExpiredStrategy());
        // ???????????????Token????????????????????????UsernamePasswordAuthenticationFilter??????
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProperty);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // ???????????????????????????????????????
        CustomizeUsernamePasswordAuthenticationFilter customizeUsernamePasswordAuthenticationFilter = new CustomizeUsernamePasswordAuthenticationFilter();
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        http.addFilterAt(customizeUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * Session????????????
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
     * ????????????
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
     * ?????????
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
     * ????????????
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
            DefaultResult<Object> result = DefaultResult.success("????????????", token);
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
        };
    }

    /**
     * ????????????
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
     * ??????
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            DefaultResult<Object> result = DefaultResult.success("????????????");
            httpServletResponse.setContentType("text/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
        };
    }

}
