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

import au.edu.tafesa.spsbuddyrestservice.entity.business.Lecturer;
import au.edu.tafesa.spsbuddyrestservice.entity.business.Student;
import au.edu.tafesa.spsbuddyrestservice.entity.business.projection.LecturerIDProjection;
import au.edu.tafesa.spsbuddyrestservice.entity.business.projection.StudentIDProjection;
import au.edu.tafesa.spsbuddyrestservice.entity.user.AppUser;
import au.edu.tafesa.spsbuddyrestservice.entity.user.UserRole;
import au.edu.tafesa.spsbuddyrestservice.model.User;
import au.edu.tafesa.spsbuddyrestservice.model.UserAuthority;
import au.edu.tafesa.spsbuddyrestservice.repository.business.LecturerRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.business.StudentRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.user.AppUserRepository;
import java.util.Optional;
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
 * Tests for AppUserDetailsService.
 * 
 * @author Fedor Gabrus
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"app.user.email.domain=tafesa.edu.au"})
public class AppUserDetailsServiceTest {
    
    @Autowired
    private AppUserDetailsService appUserDetailsService;
    
    @MockBean
    private AppUserRepository appUserRepository;
    
    @MockBean
    private StudentRepository studentRepository;
    
    @MockBean
    private LecturerRepository lecturerRepository;
    
    @Autowired
    private ProjectionFactory projectionFactory;
    
    /**
     * Test of loadUserByUsername method, of class AppUserDetailsService.
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
        
        final var expectedStudentUser = User.builder()
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
        
        final var expectedLecturerUser = User.builder()
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
        given(studentRepository.findByEmailAddress(studentEmail)).willReturn(Optional.of(studenIDProjection));
        
        given(appUserRepository.findByEmail(lecturerEmail)).willReturn(Optional.of(lecturerUser));
        given(lecturerRepository.findByEmailAddress(lecturerEmail)).willReturn(Optional.of(lecturerIDProjection));
        
        given(appUserRepository.findByEmail(studentNoIDEmail)).willReturn(Optional.of(studentUserNoID));
        given(studentRepository.findByEmailAddress(studentNoIDEmail)).willReturn(Optional.empty());
        
        given(appUserRepository.findByEmail(lecturerNoIDEmail)).willReturn(Optional.of(lecturerUserNoID));
        given(lecturerRepository.findByEmailAddress(lecturerNoIDEmail)).willReturn(Optional.empty());
        
        // Test: username (email) is null.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername(null))
                .as("username (email) is null")
                .withMessageEndingWith("has invalid pattern");
        
        // Test: username (email) is blank.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername(" "))
                .as("username (email) is blank")
                .withMessageEndingWith("has invalid pattern");
        
        // Test: email has wrong pattern.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername("student.tafesa.edu.au"))
                .as("No @ in email")
                .withMessageEndingWith("has invalid pattern");
        
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername("student@tafesa"))
                .as("No . after @")
                .withMessageEndingWith("has invalid pattern");
        
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername("@tafesa.edu.au"))
                .as("email has no prefix")
                .withMessageEndingWith("has invalid pattern");
        
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername("student@.au"))
                .as(". follows @ witout any char between them")
                .withMessageEndingWith("has invalid pattern");
        
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername("student@tafesa.edu.au."))
                .as("email ends with .")
                .withMessageEndingWith("has invalid pattern");
        
        // Test: email has wrong domain.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername("student@somedomain.au"))
                .as("email has wrong domain")
                .withMessageEndingWith("has invalid pattern");
        
        // Test: no user with provided email in appuser table.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername(noUserEmail))
                .as("no user with provided email in appuser table")
                .withMessageContaining("No user with")
                .withMessageEndingWith("email in AppUser table");
        
        // Test: student user successfully found.
        assertThat(appUserDetailsService.loadUserByUsername(studentEmail))
                .as("student user successfully found")
                .isEqualTo(expectedStudentUser);
        
        // Test: lecturer user successfully found.
        assertThat(appUserDetailsService.loadUserByUsername(lecturerEmail))
                .as("lecturer user successfully found")
                .isEqualTo(expectedLecturerUser);
        
        // Test: user has student role but no record in student table.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername(studentNoIDEmail))
                .as("user has student role but no record in student table")
                .withMessageContaining("Inconsistent data: user with email")
                .withMessageEndingWith("has no record in corresponding table of business DB");
        
        // Test: user has lecturer role but no record in lecturer table.
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> appUserDetailsService.loadUserByUsername(lecturerNoIDEmail))
                .as("user has lecturer role but no record in lecturer table")
                .withMessageContaining("Inconsistent data: user with email")
                .withMessageEndingWith("has no record in corresponding table of business DB");
    }
    
    /**
     * Configuration for injecting AppUserDetailsService in the test.
     */
    @TestConfiguration
    @Import(AppUserDetailsService.class)
    static class TestConfig {
        
        @Bean
        ProjectionFactory projectionFactory() {
            return new SpelAwareProxyProjectionFactory();
        }
        
    }
    
}
