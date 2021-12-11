package com.totwgforum.gforum.config;

import com.totwgforum.gforum.config.auth.LoginFailureHandler;
import com.totwgforum.gforum.config.auth.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.security.auth.login.FailedLoginException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/posts/update/**", "/posts/delete", "/posts/create")
                    .authenticated()
                    .antMatchers("/", "/user/create", "/user/login", "/posts/**", "/css/**")
                    .permitAll()
                // permitAll을 나중에 할 것. 순서 중요.
                    .anyRequest().authenticated()
                .and()
                    .logout().logoutSuccessUrl("/")
                .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .loginProcessingUrl("/user/login")
                    .defaultSuccessUrl("/")
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler)
                    .usernameParameter("email");

    }
}
