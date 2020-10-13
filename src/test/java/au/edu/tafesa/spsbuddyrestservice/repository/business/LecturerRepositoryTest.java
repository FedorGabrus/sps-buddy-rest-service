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

import au.edu.tafesa.spsbuddyrestservice.entity.business.Lecturer;
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
 * Test for LecturerRepository.
 * 
 * @author Fedor Gabrus
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class LecturerRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private LecturerRepository repository;

    private Lecturer lecturer;
    
    @BeforeEach
    void init() {
        lecturer = new Lecturer();
        lecturer.setEmailAddress("email");
        lecturer.setFirstName("John");
        lecturer.setLastName("Doe");
        lecturer.setLecturerID("id");
        
        entityManager.persistAndFlush(lecturer);
    }
    
    @AfterEach
    void cleanUp() {
        if (lecturer != null) {
            entityManager.remove(lecturer);
            lecturer = null;
            
            entityManager.flush();
        }
    }
    
    /**
     * Test of findByEmailAddressIs method, of class LecturerRepository.
     */
    @Test
    void testFindByEmailAddressIs() {
        assertThat(repository.findByEmailAddressIs(lecturer.getEmailAddress()).get().getLecturerID())
                .as("Obtains expected lecturer's ID")
                .isEqualTo(lecturer.getLecturerID());
        
        assertThat(repository.findByEmailAddressIs("No such email"))
                .as("Returns empty optional")
                .isEmpty();
    }
    
}
