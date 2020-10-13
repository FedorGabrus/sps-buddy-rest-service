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

import au.edu.tafesa.spsbuddyrestservice.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * UserService describes class that handles application user data.
 * 
 * @author Fedor Gabrus
 */
public interface UserService extends UserDetailsService {
    
    /**
     * Creates new authorization token and saves it to the DB.
     * Deletes old token.
     * 
     * @param forUser user that needs new token
     * @return encoded token representation
     */
    String createNewAuthorizationToken(User forUser);
    
    /**
     * Removes user's authentication token.
     * 
     * @param forUser user to log out
     */
    void deleteAuthorizationToken(User forUser);
}
