package com.boot_project.demo.config.security;

import com.boot_project.demo.config.handler.FailureAuthenticationHandler;
import com.boot_project.demo.config.handler.SuccessAuthenticationHandler;
import com.boot_project.demo.service.detailsService.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@ComponentScan("com")
@EnableOAuth2Sso
@Order(6)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private SuccessAuthenticationHandler successAuthenticationHandler;
    @Autowired
    private FailureAuthenticationHandler failureAuthenticationHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
//                .antMatchers("/", "/login**", "/webjars/**", "/error**", "/google**").permitAll()

                .mvcMatchers("/user/**").access("hasAnyRole('ADMIN', 'USER') OR hasAnyAuthority('ADMIN', 'USER')")
                .mvcMatchers("/admin/**").access("hasRole('ADMIN') or hasAuthority('ADMIN')")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/validate")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successAuthenticationHandler)
                .failureHandler(failureAuthenticationHandler)
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .logout().logoutSuccessUrl("/login");


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}

