/*
 * Copyright 2020 TAFE SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package au.edu.tafesa.spsbuddyrestservice.config;

import au.edu.tafesa.spsbuddyrestservice.component.filter.JwtAuthorizationExceptionFilter;
import au.edu.tafesa.spsbuddyrestservice.component.filter.JwtAuthorizationFilter;
import au.edu.tafesa.spsbuddyrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring security configuration.
 * Utilizes custom user details service authentication mechanism.
 * 
 * @author Fedor Gabrus
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final UserService appUserService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAuthorizationExceptionFilter jwtAuthorizationExceptionFilter;

    @Autowired
    public WebSecurityConfig(UserService appUserService, JwtAuthorizationFilter jwtAuthorizationFilter,
                             JwtAuthorizationExceptionFilter jwtAuthorizationExceptionFilter) {
        this.appUserService = appUserService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAuthorizationExceptionFilter = jwtAuthorizationExceptionFilter;
    }

    /**
     * Configures authentication manager builder.
     * 
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Generic exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Configures service to fetch application users.
        auth.userDetailsService(appUserService).passwordEncoder(passwordEncoder());
    }

    /**
     * Configures HTTP security.
     * @param http
     * @throws Exception 
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disables csrf protection as the app is stateless.
        http.cors().and().csrf().disable()
                // Configures url access.
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/login").permitAll()
                .anyRequest().authenticated()
                // Disables sessions.
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthorizationExceptionFilter, JwtAuthorizationFilter.class);
    }
    
    /**
     * Creates password encoder.
     * 
     * @return password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates authentication manager.
     * 
     * Application needs this bean to be able to use @Autowired for the authentication.
     * 
     * @return AuthenticationManager
     * @throws Exception 
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
}
