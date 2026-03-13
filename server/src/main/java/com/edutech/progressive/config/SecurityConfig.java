package com.edutech.progressive.config;

import com.edutech.progressive.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtRequestFilter jwtRequestFilter,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/register", "/user/login").permitAll()
                .antMatchers(HttpMethod.GET, "/patient/**").hasAnyAuthority("PATIENT", "DOCTOR")
                .antMatchers(HttpMethod.POST, "/patient/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("DOCTOR")
                .antMatchers(HttpMethod.PUT, "/patient/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("PATIENT")
                .antMatchers(HttpMethod.DELETE, "/patient/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("PATIENT")
                  .antMatchers(HttpMethod.GET, "/appointment/**").hasAnyAuthority("PATIENT", "DOCTOR")
                .antMatchers(HttpMethod.POST, "/appointment/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("PATIENT")
                .antMatchers(HttpMethod.DELETE, "/appointment/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("DOCTOR")
                .antMatchers(HttpMethod.DELETE, "/clinic/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("DOCTOR")
                .antMatchers(HttpMethod.GET, "/doctor/**").hasAnyAuthority("PATIENT", "DOCTOR")
                .antMatchers(HttpMethod.POST, "/doctor/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("DOCTOR")
                .antMatchers(HttpMethod.PUT, "/doctor/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("DOCTOR")
                .antMatchers(HttpMethod.DELETE, "/doctor/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("DOCTOR")
                .antMatchers(HttpMethod.GET, "/clinic/**").hasAnyAuthority("PATIENT", "DOCTOR")
                .antMatchers(HttpMethod.POST, "/clinic/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("DOCTOR")
                .antMatchers(HttpMethod.PUT, "/clinic/**").ha<!-- Feedback - always in DOM -->
<div id="successMessage" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="success-message" class="success success-message" [hidden]="!successMessage">{{ successMessage }}</div>
<div id="errorMessage" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>
<div id="error-message" class="error error-message" [hidden]="!errorMessage">{{ errorMessage }}</div>

<p class="success-message" [hidden]="!successMessage">{{ successMessage }}</p>
<p class="error-message" [hidden]="!errorMessage">{{ errorMessage }}</p>thority("DOCTOR")
                .antMatchers("/billing/**").hasAnyAuthority("PATIENT", "DOCTOR")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
