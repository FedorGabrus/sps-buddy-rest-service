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

package au.edu.tafesa.spsbuddyrestservice;

import au.edu.tafesa.spsbuddyrestservice.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Smoke test.
 *
 * @author Fedor Gabrus
 */
@SpringBootTest
class SpsBuddyRestServiceApplicationTest {

    private final AuthenticationController authenticationController;
    private final CompetencyController competencyController;
    private final LecturerController lecturerController;
    private final QualificationController qualificationController;
    private final StudentController studentController;
    private final StudyPlanController studyPlanController;
    private final SubjectController subjectController;
    private final TermDateTimeController termDateTimeController;

    @Autowired
    SpsBuddyRestServiceApplicationTest(AuthenticationController authenticationController,
                                       CompetencyController competencyController,
                                       LecturerController lecturerController,
                                       QualificationController qualificationController,
                                       StudentController studentController,
                                       StudyPlanController studyPlanController,
                                       SubjectController subjectController,
                                       TermDateTimeController termDateTimeController) {
        this.authenticationController = authenticationController;
        this.competencyController = competencyController;
        this.lecturerController = lecturerController;
        this.qualificationController = qualificationController;
        this.studentController = studentController;
        this.studyPlanController = studyPlanController;
        this.subjectController = subjectController;
        this.termDateTimeController = termDateTimeController;
    }

    /**
     * Simple smoke test.
     */
    @Test
    void contextLoads() {
        assertThat(authenticationController).as("Authentication controller loaded").isNotNull();
        assertThat(competencyController).as("Competency controller loaded").isNotNull();
        assertThat(lecturerController).as("Lecturer controller loaded").isNotNull();
        assertThat(qualificationController).as("Qualification controller").isNotNull();
        assertThat(studentController).as("Student controller loaded").isNotNull();
        assertThat(studyPlanController).as("Study plan controller loaded").isNotNull();
        assertThat(subjectController).as("Subject controller loaded").isNotNull();
        assertThat(termDateTimeController).as("Term date time controller loaded").isNotNull();
    }

}