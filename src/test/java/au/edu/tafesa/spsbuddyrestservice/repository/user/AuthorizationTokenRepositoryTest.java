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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
class AuthorizationTokenRepositoryTest {


    private final TestEntityManager entityManager;
    private final AuthorizationTokenRepository repository;

    private UserRole role;
    private AppUser user;
    private AuthorizationToken token;

    @Autowired
    AuthorizationTokenRepositoryTest(TestEntityManager entityManager, AuthorizationTokenRepository repository) {
        this.entityManager = entityManager;
        this.repository = repository;
    }

    /**
     * Tests set up.
     */
    @BeforeEach
    void eachSetUp() {
        role = new UserRole();
        role.setRoleType(UserAuthority.ROLE_STUDENT);
        entityManager.persist(role);

        user = new AppUser();
        user.setEmail("test@email");
        user.setEnabled(true);
        user.setPassword("pass");
        user.setRole(role);

        token = new AuthorizationToken(user.getEmail(), "UID", ZonedDateTime.now());
        user.setToken(token);


        entityManager.persist(user);
        entityManager.persist(token);
        entityManager.flush();

        entityManager.clear();
    }

    /**
     * Test clean up.
     */
    @AfterEach
    void cleanUp() {
        if (token != null) {
            entityManager.remove(entityManager.merge(token));
            user.setToken(null);
            token = null;
        }
        if (user != null) {
            entityManager.remove(entityManager.merge(user));
            user = null;
        }
        if (role != null) {
            entityManager.remove(entityManager.merge(role));
            role = null;
        }
        entityManager.flush();
    }

    /**
     * Test of save method.
     */
    @Test
    void testSave() {
        final var newToken = new AuthorizationToken(user.getEmail(), UUID.randomUUID().toString(), ZonedDateTime.now());

        assertThatCode(() -> repository.save(newToken))
                .as("Save doesn't throw any exceptions")
                .doesNotThrowAnyException();

        final var userFromDB = entityManager.find(AppUser.class, user.getEmail());

        assertThat(userFromDB.getToken())
                .as("Token saved to the proper user")
                .isEqualTo(newToken);

        assertThat(userFromDB.getToken().getUserEmail())
                .as("Token has expected email")
                .isEqualTo(newToken.getUserEmail());

        assertThat(userFromDB.getToken().getTokenUID())
                .as("Token has expected UID")
                .isEqualTo(newToken.getTokenUID());

        assertThat(userFromDB.getToken().getIssueDateTime())
                .as("Token has expected time stamp")
                .isEqualTo(newToken.getIssueDateTime());

        final var nextToken = new AuthorizationToken(user.getEmail(), UUID.randomUUID().toString(), ZonedDateTime.now());

        assertThatCode(() -> repository.save(nextToken))
                .as("Replaces token")
                .doesNotThrowAnyException();

        assertThat(entityManager.find(AppUser.class, user.getEmail()).getToken())
                .as("Token was replaces")
                .isEqualTo(nextToken);
    }

    /**
     * Test of deleteById method.
     */
    @Test
    void testDeleteById() {
        // Pre test check.
        assertThat(entityManager.find(AuthorizationToken.class, user.getEmail()))
                .as("Check set up: token present")
                .isNotNull();

        assertThatCode(() -> repository.deleteById(user.getEmail()))
                .as("Calling testing method")
                .doesNotThrowAnyException();

        assertThat(entityManager.find(AuthorizationToken.class, user.getEmail()))
                .as("Execution result: token was deleted")
                .isNull();

        assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> repository.deleteById(user.getEmail()))
                .as("Deletion of not existing row throws exception");

        // Clean up class fields.
        user.setToken(null);
        token = null;
    }

}