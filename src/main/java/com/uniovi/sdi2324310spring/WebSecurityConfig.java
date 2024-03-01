package com.uniovi.sdi2324310spring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/css/**", "/images/**", "/script/**", "/", "/signup", "/login/**").permitAll()
                    .antMatchers("/mark/add").hasAuthority("ROLE_PROFESSOR")
                    .antMatchers("/mark/edit/*").hasAuthority("ROLE_PROFESSOR")
                    .antMatchers("/mark/delete/*").hasAuthority("ROLE_PROFESSOR")
                    .antMatchers("/mark/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN")
                    .antMatchers("/user/**").hasAnyRole("ADMIN")
                    .antMatchers("/professor/add").hasAnyAuthority("ROLE_ADMIN")
                    .antMatchers("/professor/edit/*").hasAnyAuthority("ROLE_ADMIN")
                    .antMatchers("/professor/details/*").hasAnyAuthority("ROLE_ADMIN","ROLE_PROFESSOR")
                    .antMatchers("/professor/list").hasAnyAuthority("ROLE_ADMIN","ROLE_PROFESSOR")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/home")
                    .and()
                .logout()
                    .permitAll();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
}