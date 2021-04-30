package com.spring.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // List of routes (URL) without authentication
    private static final String[] AUTH_LISTS ={
            "/",
            "/posts",
            "/post/{id}"
    } ;

    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers(AUTH_LISTS).permitAll()
                    .anyRequest().authenticated()
                    .and().
                formLogin()
                    .permitAll()
                    .and().
                logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override
    protected void  configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth
                .inMemoryAuthentication()
                    .withUser("georgina")
                    .password("{noop}123")
                    .roles("ADMIN");
    }

    //this method allows static resources to be neglected by spring security
    @Override
    public  void  configure(WebSecurity web) throws  Exception{
        web
                .ignoring()
                .antMatchers("/bootstraps/**");
    }
}
