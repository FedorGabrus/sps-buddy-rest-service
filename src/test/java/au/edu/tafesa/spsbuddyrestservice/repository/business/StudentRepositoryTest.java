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

package au.edu.tafesa.spsbuddyrestservice.repository.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
class StudentRepositoryTest {

    private final TestEntityManager entityManager;
    private final StudentRepository repository;
    private Student student;

    @Autowired
    StudentRepositoryTest(TestEntityManager entityManager, StudentRepository repository) {
        this.entityManager = entityManager;
        this.repository = repository;

        student = null;
    }

    @BeforeEach
    void init() {
        student = new Student();
        student.setStudentID("studId");
        student.setEmailAddress("student@email");
        student.setFirstName("John");
        student.setLastName("Doe");

        student = entityManager.persistAndFlush(student);
    }

    @AfterEach
    void cleanUp() {
        if (student != null) {
            entityManager.remove(student);
            student = null;
            entityManager.flush();
        }
    }

    @Test
    void testFindByEmailAddressIsIgnoreCase() {
        assertThat(repository.findByEmailAddressIsIgnoreCase(student.getEmailAddress()))
                .as("Returned value is not empty")
                .isNotNull();

        assertThat(repository.findByEmailAddressIsIgnoreCase(student.getEmailAddress()).get().getStudentID())
                .as("Contains expected student's id")
                .isEqualTo(student.getStudentID());

        assertThat(repository.findByEmailAddressIsIgnoreCase(student.getEmailAddress().toUpperCase(Locale.ROOT))
                .get().getStudentID())
                .as("Searches ignoring case")
                .isEqualTo(student.getStudentID());

        assertThat(repository.findByEmailAddressIsIgnoreCase("No such email"))
                .as("Returns empty optional if student not found")
                .isEmpty();
    }

    @Test
    void testFindById() {
        assertThat(repository.findById(student.getStudentID()))
                .as("Returned value is not empty")
                .isNotNull();

        assertThat(repository.findById(student.getStudentID()).get().getStudentID())
                .as("Returns proper student")
                .isEqualTo(student.getStudentID());

        assertThat(repository.findById("No such id"))
                .as("Returns empty optional if student not found")
                .isEmpty();
    }

}