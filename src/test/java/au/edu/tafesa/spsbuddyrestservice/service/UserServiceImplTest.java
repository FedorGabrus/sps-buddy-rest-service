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
import au.edu.tafesa.spsbuddyrestservice.entity.business.Lecturer;
import au.edu.tafesa.spsbuddyrestservice.entity.business.Student;
import au.edu.tafesa.spsbuddyrestservice.entity.business.projection.LecturerIDProjection;
import au.edu.tafesa.spsbuddyrestservice.entity.business.projection.StudentIDProjection;
import au.edu.tafesa.spsbuddyrestservice.entity.user.AppUser;
import au.edu.tafesa.spsbuddyrestservice.entity.user.AuthorizationToken;
import au.edu.tafesa.spsbuddyrestservice.entity.user.UserRole;
import au.edu.tafesa.spsbuddyrestservice.exception.InconsistentDataException;
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
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests for UserServiceImpl.
 * 
 * @author Fedor Gabrus
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"app.user.email.domain=tafesa.edu.au"})
public class UserServiceImplTest {
    
    @Autowired
    private UserServiceImpl appUserService;
    
    @MockBean
    private AppUserRepository appUserRepository;
    
    @MockBean
    private AuthorizationTokenRepository authorizationTokenRepository;
    
    @MockBean
    private StudentRepository studentRepository;
    
    @MockBean
    private LecturerRepository lecturerRepository;
    
    @Autowired
    private ProjectionFactory projectionFactory;
    
    @MockBean
    private JwsUtility jwsUtility;
    
    /**
     * Configuration for injecting UserServiceImpl in the test.
     */
    @TestConfiguration
    @Import(UserServiceImpl.class)
    static class TestConfig {

        @Bean
        ProjectionFactory projectionFactory() {
            return new SpelAwareProxyProjectionFactory();
        }

    }
    
    /**
     * Test of loadUserByUsername method, of class UserServiceImpl.
     */
    @Test
    public void testLoadUserByUsername() {
        // Test set up.
        final String studentEmail = "student@student.tafesa.edu.au";
        final String lecturerEmail = "lecturer@tafesa.edu.au";
        final String noUserEmail = "nouser@tafesa.edu.au";
        final String studentID = "studentID";
        final String lecturerID = "lecturerID";
        final String studentNoIDEmail = "studentNoId@student.tafesa.edu.au";
        final String lecturerNoIDEmail = "lecturerNoId@tafesa.edu.au";
        
        final var studentRole = new UserRole();
        studentRole.setRoleID(1);
        studentRole.setRoleType(UserAuthority.ROLE_STUDENT);
        
        final var studentUser = new AppUser();
        studentUser.setEmail(studentEmail);
        studentUser.setPassword("pass");
        studentUser.setEnabled(true);
        studentUser.setRole(studentRole);
        
        final var student = new Student();
        student.setStudentID(studentID);
        student.setEmailAddress(studentUser.getEmail());
        student.setFirstName("John");
        student.setLastName("Doe");
        
        final var expectedStudentUser = UserImpl.builder()
                .email(studentUser.getEmail())
                .enabled(studentUser.isEnabled())
                .password(studentUser.getPassword())
                .role(studentUser.getRole().getRoleType())
                .userId(student.getStudentID())
                .build();
        
        final var studentUserNoID = new AppUser();
        studentUserNoID.setEmail(studentNoIDEmail);
        studentUserNoID.setPassword("pass");
        studentUserNoID.setEnabled(false);
        studentUserNoID.setRole(studentRole);
        
        final var studenIDProjection = projectionFactory.createProjection(StudentIDProjection.class);
        studenIDProjection.setStudentID(studentID);
        
        final var lecturerRole = new UserRole();
        lecturerRole.setRoleID(2);
        lecturerRole.setRoleType(UserAuthority.ROLE_LECTURER);
        
        final var lecturerUser = new AppUser();
        lecturerUser.setEmail(lecturerEmail);
        lecturerUser.setPassword("password");
        lecturerUser.setEnabled(false);
        lecturerUser.setRole(lecturerRole);
        
        final var lecturer = new Lecturer();
        lecturer.setLecturerID(lecturerID);
        lecturer.setEmailAddress(lecturerUser.getEmail());
        lecturer.setFirstName("John");
        lecturer.setLastName("Doe");
        
        final var expectedLecturerUser = UserImpl.builder()
                .email(lecturerUser.getEmail())
                .enabled(lecturerUser.isEnabled())
                .password(lecturerUser.getPassword())
                .role(lecturerUser.getRole().getRoleType())
                .userId(lecturer.getLecturerID())
                .build();
        
        final var lecturerUserNoID = new AppUser();
        lecturerUserNoID.setEmail(lecturerNoIDEmail);
        lecturerUserNoID.setPassword("pass");
        lecturerUserNoID.setEnabled(true);
        lecturerUserNoID.setRole(lecturerRole);
        
        final var lecturerIDProjection = projectionFactory.createProjection(LecturerIDProjection.class);
        lecturerIDProjection.setLecturerID(lecturerID);
        
        given(appUserRepository.findByEmail(noUserEmail)).willReturn(Optional.empty());
        
        given(appUserRepository.findByEmail(studentEmail)).willReturn(Optional.of(studentUser));
        given(studentRepository.findByEmailAddressIs(studentEmail)).willReturn(Optional.of(studenIDProjection));
        
        given(appUserRepository.findByEmail(lecturerEmail)).willReturn(Optional.of(lecturerUser));
        given(lecturerRepository.findByEmailAddressIs(lecturerEmail)).willReturn(Optional.of(lecturerIDProjection));
        
        given(appUserRepository.findByEmail(studentNoIDEmail)).willReturn(Optional.of(studentUserNoID));
        given(studentRepository.findByEmailAddressIs(studentNoIDEmail)).willReturn(Optional.empty());
        
        given(appUserRepository.findByEmail(lecturerNoIDEmail)).willReturn(Optional.of(lecturerUserNoID));
        given(lecturerRepository.findByEmailAddressIs(lecturerNoIDEmail)).willReturn(Optional.empty());
        
        // Test: username (email) is null.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername(null))
                .as("username (email) is null")
                .withMessageEndingWith("has invalid pattern");
        
        // Test: username (email) is blank.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername(" "))
                .as("username (email) is blank")
                .withMessageEndingWith("has invalid pattern");
        
        // Test: email has wrong pattern.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername("student.tafesa.edu.au"))
                .as("No @ in email")
                .withMessageEndingWith("has invalid pattern");
        
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername("student@tafesa"))
                .as("No . after @")
                .withMessageEndingWith("has invalid pattern");
        
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername("@tafesa.edu.au"))
                .as("email has no prefix")
                .withMessageEndingWith("has invalid pattern");
        
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername("student@.au"))
                .as(". follows @ witout any char between them")
                .withMessageEndingWith("has invalid pattern");
        
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername("student@tafesa.edu.au."))
                .as("email ends with .")
                .withMessageEndingWith("has invalid pattern");
        
        // Test: email has wrong domain.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername("student@somedomain.au"))
                .as("email has wrong domain")
                .withMessageEndingWith("has invalid pattern");
        
        // Test: no user with provided email in appuser table.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername(noUserEmail))
                .as("no user with provided email in appuser table")
                .withMessageContaining("No user with")
                .withMessageEndingWith("email in AppUser table");
        
        // Test: student user successfully found.
        assertThat(appUserService.loadUserByUsername(studentEmail))
                .as("student user successfully found")
                .isEqualTo(expectedStudentUser);
        
        // Test: lecturer user successfully found.
        assertThat(appUserService.loadUserByUsername(lecturerEmail))
                .as("lecturer user successfully found")
                .isEqualTo(expectedLecturerUser);
        
        // Test: user has student role but no record in student table.
        assertThatExceptionOfType(InconsistentDataException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername(studentNoIDEmail))
                .as("user has student role but no record in student table")
                .withMessageContaining("Inconsistent data: user with email")
                .withMessageEndingWith("has no record in corresponding table of business DB");
        
        // Test: user has lecturer role but no record in lecturer table.
        assertThatExceptionOfType(InconsistentDataException.class)
                .isThrownBy(() -> appUserService.loadUserByUsername(lecturerNoIDEmail))
                .as("user has lecturer role but no record in lecturer table")
                .withMessageContaining("Inconsistent data: user with email")
                .withMessageEndingWith("has no record in corresponding table of business DB");
    }

    /**
     * Test of createNewAuthorizationToken method, of class UserServiceImpl.
     */
    @Test
    public void testCreateNewAuthorizationToken() {
        // Test setup.
        final var user = UserImpl.builder()
                .email("email")
                .enabled(true)
                .password("pass")
                .role(UserAuthority.ROLE_STUDENT)
                .userId("id")
                .build();
        
        final var role = new UserRole();
        role.setRoleID(1);
        role.setRoleType(user.getRole());
        
        final var tokenEntity = new AuthorizationToken(user.getEmail(), UUID.randomUUID().toString(),
                ZonedDateTime.now());
        
        given(authorizationTokenRepository.save(any(AuthorizationToken.class))).willReturn(tokenEntity);
        
        // Test that passed in user was updated with the new token.
        assertThat(user.getAuthToken())
                .as("Test set up: user has no token")
                .isEmpty();
        
        assertThatCode(() -> appUserService.createNewAuthorizationToken(user))
                .as("Normal excecution doesn't throw exception")
                .doesNotThrowAnyException();
        
        assertThat(user.getAuthToken().get().getUid())
                .as("User's token has expected UID")
                .isEqualTo(tokenEntity.getTokenUID());
        
        assertThat(user.getAuthToken().get().getIssuedAt())
                .as("User's token has expected time stamp")
                .isEqualTo(tokenEntity.getIssueDateTime());
    }
    
    /**
     * Test of deleteAuthorizationToken method.
     */
    @Test
    void testDeleteAuthorizationToken() {
        // Test set up.
        final var userNoToken = UserImpl.builder()
                .email("email")
                .password("pass")
                .role(UserAuthority.ROLE_STUDENT)
                .userId("id")
                .build();
        
        final var userWithToken = UserImpl.builder()
                .email("another email")
                .password("12345")
                .role(UserAuthority.ROLE_LECTURER)
                .userId("another id")
                .authToken(new UserToken("uid", ZonedDateTime.now()))
                .build();
        
        // Test: throws exception if User has no token.
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> appUserService.deleteAuthorizationToken(userNoToken))
                .as("User has no token")
                .withMessage("Attempt to delete non-existent token");
        
        // Test: removes token from passed User object.
        assertThat(userWithToken.getAuthToken())
                .as("Test set up: user should have token")
                .isNotEmpty();
        
        assertThatCode(() -> appUserService.deleteAuthorizationToken(userWithToken))
                .as("No exception when user has token")
                .doesNotThrowAnyException();
        
        assertThat(userWithToken.getAuthToken())
                .as("Token was removed from passed in user object")
                .isEmpty();
    }
    
}
