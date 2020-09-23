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
package au.edu.tafesa.spsbuddyrestservice.repository.user;

import au.edu.tafesa.spsbuddyrestservice.entity.user.AppUser;
import au.edu.tafesa.spsbuddyrestservice.entity.user.AuthorizationToken;
import au.edu.tafesa.spsbuddyrestservice.entity.user.UserRole;
import au.edu.tafesa.spsbuddyrestservice.model.UserAuthority;
import java.time.ZonedDateTime;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests for AppUserRepository.
 * Uses in memory H2 database for testing.
 *
 * @author Fedor Gabrus
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class AppUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppUserRepository repository;

    /**
     * Test of findByEmail method, of class AppUserRepository.
     */
    @Test
    public void testFindByEmail() {
        //Test set up.
        final String userEmail = "test@test.com";
        final var tokenIssuedAt = ZonedDateTime.now();

        final var role = new UserRole();
        role.setRoleType(UserAuthority.ROLE_STUDENT);

        final var userWithToken = new AppUser();
        userWithToken.setEmail(userEmail);
        userWithToken.setPassword("password");
        userWithToken.setEnabled(true);
        userWithToken.setRole(role);

        final var token = new AuthorizationToken();
        token.setIssueDateTime(tokenIssuedAt);
        token.setTokenUID("UID");
        token.setUser(userWithToken);

        userWithToken.setToken(token);
        
        final String noTokenUserEmail = "notoken@test.com";
        final var userWithoutToken = new AppUser();
        userWithoutToken.setEmail(noTokenUserEmail);
        userWithoutToken.setPassword("pass");
        userWithoutToken.setRole(role);

        entityManager.persist(role);
        entityManager.persist(userWithToken);
        entityManager.persist(userWithoutToken);
        entityManager.flush();
        
        // Tests.
        assertThatCode(() -> repository.findByEmail(userEmail))
                .as("findByEmail doesn't throw any Exception")
                .doesNotThrowAnyException();

        assertThat(repository.findByEmail("no-such-email"))
                .as("findByEmail returns an empty optional")
                .isEmpty();

        assertThat(repository.findByEmail(userEmail))
                .as("findByEmail returns expected user")
                .hasValue(userWithToken);
        
        assertThat(repository.findByEmail(userEmail).get().getRole())
                .as("User has expected role")
                .isEqualTo(role);
        
        assertThat(repository.findByEmail(userEmail).get().getToken())
                .as("User has expected token")
                .isEqualTo(token);
        
        assertThat(repository.findByEmail(noTokenUserEmail))
                .as("Gets user without token")
                .hasValue(userWithoutToken);
        
        assertThat(repository.findByEmail(noTokenUserEmail).get().getToken())
                .as("Null when no token")
                .isNull();
    }

}
