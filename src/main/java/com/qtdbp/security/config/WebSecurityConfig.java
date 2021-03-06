package com.qtdbp.security.config;

import com.qtdbp.security.system.CustomFilterSecurityInterceptor;
import com.qtdbp.security.system.CustomLoginSuccessHandler;
import com.qtdbp.security.system.CustomUserDetailsService;
import com.qtdbp.security.system.SimpleRememberMeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * 钱塘交易中心应用权限框架配置
 *
 * @author: caidchen
 * @create: 2017-04-12 14:02
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService defaultUserDetailsService ;

//    @Autowired
//    private RequestAccessDeniedHandler accessDeniedHandler ;

    @Autowired
    private CustomFilterSecurityInterceptor customFilterSecurityInterceptor;

    private static final String KEY = "admin_key" ;

    /**
     * http://localhost:8080/login 输入正确的用户名密码 并且选中remember-me 则登陆成功，转到 index页面
     * 再次访问index页面无需登录直接访问
     * 访问http://localhost:8080/home 不拦截，直接访问，
     * 访问http://localhost:8080/hello 需要登录验证后，且具备 “ADMIN”权限hasAuthority("ADMIN")才可以访问
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // 关闭crsf() 防止post请求405

        //        http.httpBasic().disable();

        http.authorizeRequests()
                .antMatchers("/login/**").anonymous()
                .anyRequest().authenticated();

        // 配置登录, 指定登录页是”/login”
//        http.formLogin().disable();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .successHandler(loginSuccessHandler()) ;

//        http.exceptionHandling()
////                .accessDeniedHandler(accessDeniedHandler)
//                .authenticationEntryPoint(new LoginAuthenticationEntryPoint("/login"));

        // 配置登出
        http.logout()
                .logoutSuccessUrl("/login")
                .permitAll();

        // 登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
        http.rememberMe()
                .rememberMeServices(new SimpleRememberMeServices(KEY, defaultUserDetailsService))
                .tokenValiditySeconds(1209600) ;

        // 检测失效的sessionId,超时时定位到另外一个URL
        http.sessionManagement().invalidSessionUrl("/timeout") ;

        // 数据库管理资源，权限校验
        http.addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //指定密码加密所使用的加密器为passwordEncoder()
        //需要将密码加密后写入数据库
        auth.userDetailsService(defaultUserDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //设置不拦截规则
        web.ignoring().antMatchers("classpath:resources/static/**");
    }

    /**
     * 对于密码做加密处理
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    /**
     * 存储用户信息
     * @return
     */
    @Bean
    public CustomLoginSuccessHandler loginSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

}
