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
package au.edu.tafesa.spsbuddyrestservice.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * User interface describes class that provides core app user's information.
 * User can have only one role in the app.
 * 
 * @author Fedor Gabrus
 */
public interface User extends UserDetails {
    
    /**
     * Gets user's ID.
     * 
     * @return user's id
     */
    String getUserId();
    
    /**
     * Obtains user's authorization token.
     * 
     * @return user's authorization token wrapped in optional
     */
    Optional<UserToken> getAuthToken();
    
    /**
     * Sets authToken for user.
     * 
     * @param authToken new auth token
     */
    void setAuthToken(UserToken authToken);
    
    /**
     * Gets name of user's role.
     * 
     * @return name of user's role.
     */
    String getRoleName();
    
    /**
     * Gets user's role.
     * 
     * @return user's role
     */
    UserAuthority getRole();
    
    /**
     * Gets user's email.
     * 
     * @return user's email
     */
    String getEmail();

    /**
     * Checks if user has provided id.
     *
     * @param id id to check
     * @return true if user has the same id, false otherwise
     */
    boolean hasThisId(String id);

    /**
     * Checks if user has provided authority.
     *
     * @param userAuthority authority to check
     * @return true if user has provided authority, false otherwise
     */
    boolean hasThisAuthority(UserAuthority userAuthority);

    /**
     * Checks whether user is a student.
     *
     * @return true if user is a student, false otherwise
     */
    boolean isStudent();

    /**
     * Checks whether user is a lecturer.
     *
     * @return true if user is a lecturer, false otherwise
     */
    boolean isLecturer();
}
