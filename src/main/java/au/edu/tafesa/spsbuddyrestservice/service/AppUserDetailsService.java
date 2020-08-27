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
package au.edu.tafesa.spsbuddyrestservice.service;

import au.edu.tafesa.spsbuddyrestservice.entity.user.AppUser;
import au.edu.tafesa.spsbuddyrestservice.model.AppUserDetails;
import au.edu.tafesa.spsbuddyrestservice.repository.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Handles application users extraction.
 * 
 * @author Fedor Gabrus
 */
@Service
public class AppUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Value("${app.security.exception.UsernameNotFoundException}")
    private String usernameNotFoundExceptionText;
    
    @Value("${app.user.email.domain}")
    private String userEmailDomain;

    /**
     * Retrieves UserDetails.
     * Searches user by school email.
     * 
     * @param username school email
     * @return AppUserDetails retrieved user's data
     * @throws UsernameNotFoundException when user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!validateUsername(username)) {
            throw new UsernameNotFoundException(usernameNotFoundExceptionText);
        }
        
        // Gets user by user email or throws exception.
        final AppUser user = appUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(usernameNotFoundExceptionText));
        
        return new AppUserDetails(user.getEmail(), user.getPassword(), user.isEnabled(), user.getUserRoleName());
    }
    
    /**
     * Validates username according to the patterns.
     * Prevents DB querying if username has a bad format.
     * Acceptable usernames: student or lecturer IDs or school email.
     * 
     * Assumes that id has the same length and contains only digits.
     * Assumes that school email use only dots and underscore as the delimiter and has the same ending.
     * 
     * @param username username to validate
     * @return true if username has a valid pattern, false otherwise.
     */
    private boolean validateUsername(String username) {
        return (username != null) && !username.isBlank()
                && (username.matches("^\\w+(\\.\\w+)*[@]{1}\\w+(\\.\\w+)+$") && username.endsWith(userEmailDomain));
    }
    
}
