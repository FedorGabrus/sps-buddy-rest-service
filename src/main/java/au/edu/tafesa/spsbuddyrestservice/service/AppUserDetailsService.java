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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Handles application users data.
 * 
 * @author Fedor Gabrus
 */
@Service
@Slf4j
public class AppUserDetailsService implements UserDetailsService {
    
    // Text for UsernameNotFoundException.
    private static final String USER_NOT_FOUND = "User not founds";
    
    // Email domain for username validation.
    @Value("${app.user.email.domain}")
    private String userEmailDomain;
    
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Retrieves UserDetails.
     * Searches user by school email.
     * 
     * @param userEmail school email
     * @return AppUserDetails retrieved user's data
     * @throws UsernameNotFoundException when user not found
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // Validates email.
        if (!validateUserEmail(userEmail)) {
            log.debug("Email " + userEmail + " has invalid pattern");
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        
        // Gets user by user email or throws exception.
        final AppUser user = appUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    log.debug("No user with " + userEmail + " email");
                    return new UsernameNotFoundException(USER_NOT_FOUND);
                });
        
        return new AppUserDetails(user.getEmail(), user.getPassword(), user.isEnabled(), user.getUserRoleName());
    }
    
    /**
     * Validates user's email according to the patterns.
     * Prevents DB querying if email has a bad format.
     * 
     * Assumes that school email use only dots and underscore as the delimiter and has the same ending.
     * 
     * @param userEmail userEmail to validate
     * @return true if userEmail has a valid pattern, false otherwise.
     */
    private boolean validateUserEmail(String userEmail) {
        return (userEmail != null) && !userEmail.isBlank()
                && (userEmail.matches("^\\w+(\\.\\w+)*[@]{1}\\w+(\\.\\w+)+$") && userEmail.endsWith(userEmailDomain));
    }
    
}
