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
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests StudentRepository.
 * Utilizes in memory H2 database.
 * 
 * @author Fedor Gabrus
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository repository;
    
    private Student student;
    
    @BeforeEach
    void init() {
        student = new Student();
        student.setEmailAddress("email");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setStudentID("studId");
        
        entityManager.persistAndFlush(student);
    }
    
    @AfterEach
    void cleanUp() {
        if (student != null) {
            entityManager.remove(student);
            student = null;
            entityManager.flush();
        }
    }
    
    /**
     * Test of findByEmailAddressIs method, of class StudentRepository.
     */
    @Test
    void testFindByEmailAddressIs() {
        assertThatCode(() -> repository.findByEmailAddressIs(student.getEmailAddress()))
                .as("Doesn't throw Exceptions")
                .doesNotThrowAnyException();
        
        assertThat(repository.findByEmailAddressIs(student.getEmailAddress()).get().getStudentID())
                .as("Obtains expected student ID")
                .isEqualTo(student.getStudentID());
        
        
        assertThat(repository.findByEmailAddressIs("No such email"))
                .as("Returns empty optional")
                .isEmpty();
    }
    
}
