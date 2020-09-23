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
import au.edu.tafesa.spsbuddyrestservice.model.User;
import au.edu.tafesa.spsbuddyrestservice.model.UserAuthority;
import au.edu.tafesa.spsbuddyrestservice.model.UserToken;
import au.edu.tafesa.spsbuddyrestservice.repository.business.LecturerRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.business.StudentRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.user.AppUserRepository;
import java.util.Optional;
import lombok.NonNull;
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

    // Email domain for username validation.
    @Value("${app.user.email.domain}")
    private String userEmailDomain;

    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private LecturerRepository lecturerRepository;

    /**
     * Retrieves UserDetails. Searches user by school email.
     * Throws UsernameNotFoundException when user not found by email or when no user with provided email
     * in Student or Lecturer tables or when user's role is different from student or lecturer.
     * 
     * 
     * @param userEmail school email
     * @return User retrieved user's data
     * @throws UsernameNotFoundException when user not found or inconsistent data in users and business DB
     * or when role is different from student or lecturer.
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // Throws exception if email has invalid pattern.
        if (isEmailHasInvalidPattern(userEmail)) {
            final String error = "Email '" + userEmail + "' has invalid pattern";
            log.debug(error);
            throw new UsernameNotFoundException(error);
        }

        // Gets user by user email or throws exception.
        final AppUser user = appUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    final String error = "No user with '" + userEmail + "' email in AppUser table";
                    log.debug(error);
                    return new UsernameNotFoundException(error);
                });
        
        // Obtains user's id depending on the role. If no userID found, throws exception.
        final String userId = fetchUserID(user.getEmail(), user.getRole().getRoleType())
                .orElseThrow(() -> {
                    final String error = "Inconsistent data: user with email: '"
                            + user.getEmail()
                            + "' and role: '"
                            + user.getRole().getRoleType()
                            + "' has no record in corresponding table of business DB";
                    log.error(error);
                    return new UsernameNotFoundException(error);
                });
        
        // Builds user model.
        return User.builder()
                .userId(userId)
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .role(user.getRole().getRoleType())
                .authToken((user.getToken() == null) ? null
                        : new UserToken(user.getToken().getTokenUID(), user.getToken().getIssueDateTime()))
                .build();
    }

    /**
     * Validates user's email according to the patterns. Prevents DB querying if email has a bad format.
     *
     * Assumes that school email use only dots and underscore as the delimiter and has the same ending.
     *
     * @param userEmail userEmail to validate
     * @return true if userEmail has a invalid pattern, false otherwise.
     */
    private boolean isEmailHasInvalidPattern(String userEmail) {
        return (userEmail == null)
                || userEmail.isBlank()
                || (!userEmail.matches("^\\w+(\\.\\w+)*[@]{1}\\w+(\\.\\w+)+$")
                || (!userEmail.endsWith(userEmailDomain)));
    }
    
    /**
     * Obtains studentIDProjection or lecturerIDProjection's ID depending on the role.
     * 
     * @param userEmail email to look up
     * @param userRole user's role in the app
     * @return studentIDProjection/lecturerIDProjection ID or empty optional when role unknown
     */
    private Optional<String> fetchUserID(@NonNull String userEmail, @NonNull UserAuthority userRole) {
        switch (userRole) {
            case ROLE_STUDENT:
                // Obtains student's id.
                return studentRepository.findByEmailAddress(userEmail)
                        .map(studentIDProjection -> studentIDProjection.getStudentID());
                
            case ROLE_LECTURER:
                // Obtains lecturer's id.
                return lecturerRepository.findByEmailAddress(userEmail)
                        .map(lecturerIDProjection -> lecturerIDProjection.getLecturerID());
                
            default:
                // Returns empty optional if role unknown.
                return Optional.empty();
        }
    }
    
}
