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

import au.edu.tafesa.spsbuddyrestservice.component.JwsUtility;
import au.edu.tafesa.spsbuddyrestservice.entity.user.AppUser;
import au.edu.tafesa.spsbuddyrestservice.entity.user.AuthorizationToken;
import au.edu.tafesa.spsbuddyrestservice.exception.InconsistentDataException;
import au.edu.tafesa.spsbuddyrestservice.model.User;
import au.edu.tafesa.spsbuddyrestservice.model.UserImpl;
import au.edu.tafesa.spsbuddyrestservice.model.UserAuthority;
import au.edu.tafesa.spsbuddyrestservice.model.UserToken;
import au.edu.tafesa.spsbuddyrestservice.repository.business.LecturerRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.business.StudentRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.user.AppUserRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.user.AuthorizationTokenRepository;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Handles application users data.
 *
 * @author Fedor Gabrus
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    // Email domain for username validation.
    @Value("${app.user.email.domain}")
    private String userEmailDomain;

    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private AuthorizationTokenRepository authorizationTokenRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private LecturerRepository lecturerRepository;
    
    @Autowired
    private JwsUtility jwsUtility;

    /**
     * Retrieves UserDetails. Searches user by school email.
     * Throws UsernameNotFoundException when user not found by email or when no user with provided email
     * in Student or Lecturer tables or when user's role is different from student or lecturer.
     * 
     * @param userEmail school email
     * @return UserImpl retrieved user's data
     * @throws UsernameNotFoundException when user not found
     * @throws InconsistentDataException throws when no user in corresponding business DB table
     */
    @Override
    public User loadUserByUsername(String userEmail) throws UsernameNotFoundException, InconsistentDataException {
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
                    return new InconsistentDataException(error);
                });
        
        // Builds user model.
        return UserImpl.builder()
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
                return studentRepository.findByEmailAddressIs(userEmail)
                        .map(studentIDProjection -> studentIDProjection.getStudentID());
                
            case ROLE_LECTURER:
                // Obtains lecturer's id.
                return lecturerRepository.findByEmailAddressIs(userEmail)
                        .map(lecturerIDProjection -> lecturerIDProjection.getLecturerID());
                
            default:
                // Returns empty optional if role unknown.
                return Optional.empty();
        }
    }

    /**
     * Creates new authorization token and persists it in the DB.
     * Updates passed in User object.
     * 
     * @param forUser user that needs new tokenEntity. Not null
     * @return encoded tokenEntity representation
     */
    @Override
    public String createNewAuthorizationToken(@NonNull User forUser) {
        // Creates new tokenEntity entity.
        var tokenEntity = new AuthorizationToken(forUser.getEmail(), UUID.randomUUID().toString(),
                ZonedDateTime.now());
        
        // Persists token.
        tokenEntity = authorizationTokenRepository.save(tokenEntity);
        
        // Updates for user.
        forUser.setAuthToken(new UserToken(tokenEntity.getTokenUID(), tokenEntity.getIssueDateTime()));
        
        // Encodes tokenEntity as JWT.
        return jwsUtility.createEncodedJws(tokenEntity.getUserEmail(), tokenEntity.getTokenUID(),
                tokenEntity.getIssueDateTime());
    }

    /**
     * Removes authorization tokenEntity for user.
     * 
     * @param forUser user to remove tokenEntity
     */
    @Override
    public void deleteAuthorizationToken(@NonNull User forUser) {
        // Deletes token or logs error.
        forUser.getAuthToken().ifPresentOrElse(
                val -> {
                    // Deletes token from the DB.
                    authorizationTokenRepository.deleteById(forUser.getEmail());
                    // Removes token from User object.
                    forUser.setAuthToken(null);
                },
                () -> {
                    final String error = "Attempt to delete non-existent token";
                    log.error(error);
                    throw new RuntimeException(error);
                });
    }
    
}
