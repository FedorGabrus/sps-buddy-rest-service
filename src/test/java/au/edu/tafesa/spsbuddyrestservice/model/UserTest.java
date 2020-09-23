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

import java.time.ZonedDateTime;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Unit test for User model.
 * 
 * @author Fedor Gabrus
 */
public class UserTest {
    
    /**
     * Test for constructor.
     */
    @Test
    void testConstructor() {
        assertThatCode(
                () -> new User("id", "email", "pass", true, UserAuthority.ROLE_STUDENT,
                        new UserToken("uid", ZonedDateTime.now()))
        ).doesNotThrowAnyException();
        
        assertThatCode(() -> new User(null, "email", "pass", false, UserAuthority.ROLE_LECTURER, null))
                .doesNotThrowAnyException();
        
        assertThat(new User(null, "email", "pass", false, UserAuthority.ROLE_LECTURER, null))
                .isInstanceOf(UserDetails.class)
                .isNotNull();
        
        assertThatNullPointerException().isThrownBy(() -> new User(null, null, null, true, null, null));
        
        assertThatNullPointerException().isThrownBy(() ->
                new User("id", null, "pass", true, UserAuthority.ROLE_STUDENT, null));
        
        assertThatNullPointerException().isThrownBy(() -> 
                new User("id", "email", null, true, UserAuthority.ROLE_STUDENT, null));
        
        assertThatNullPointerException().isThrownBy(() -> new User("id", "email", "pass", false, null, null));
    }
    
    /**
     * Test for getAuthorities.
     */
    @Test
    void testGetAuthorities() {
        final var instance = new User("id", "email", "pass", true, UserAuthority.ROLE_STUDENT, null);
        
        assertThat(instance.getAuthorities())
                .as("Returns proper authorities")
                .isNotEmpty()
                .asList()
                .containsOnly(new SimpleGrantedAuthority(UserAuthority.ROLE_STUDENT.toString()));   
    }
    
    /**
     * Test for getUsername.
     */
    @Test
    void testGetUsername() {
        final String username = "email";
        final var instance = new User("id", username, "pass", true, UserAuthority.ROLE_STUDENT, null);
        
        assertThat(instance.getUsername())
                .as("Obtains proper filed as username")
                .isEqualTo(username);
    }
    
    /**
     * Test for getPassword.
     */
    @Test
    void testGetPassword() {
        final String password = "pass";
        final var instance = new User("id", "user", password, false, UserAuthority.ROLE_STUDENT, null);
        
        assertThat(instance.getPassword())
                .isEqualTo(password);
    }
    
    /**
     * Test for isEnabled.
     */
    @Test
    void testIsEnabled() {
        final boolean enabled = false;
        final var instance = new User("id", "user", "pass", enabled, UserAuthority.ROLE_STUDENT, null);
        
        assertThat(instance.isEnabled())
                .isFalse();
    }
    
}
