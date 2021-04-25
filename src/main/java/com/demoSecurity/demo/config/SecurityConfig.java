package com.demoSecurity.demo.config;

import com.demoSecurity.demo.model.User;
import com.demoSecurity.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                //.antMatchers("/api/user/**").hasRole("USER")//USER role can access /users/**.antMatchers("/admin/**").hasRole("ADMIN")//ADMIN role can access /admin/**
                //.antMatchers("/api/admin/**").hasRole("ADMIN")//USER role can access /shared/**
                .antMatchers("/**", "/logout").permitAll()// anyone can access /public/**
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST,"/api/user").permitAll()
                .anyRequest().authenticated()//any other request just need authentication
                .and()
                .addFilterBefore(new LoginFilter("/login",authenticationManager()),UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
        }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }
}
