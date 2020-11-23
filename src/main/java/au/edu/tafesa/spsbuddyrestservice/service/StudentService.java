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

import au.edu.tafesa.spsbuddyrestservice.model.EnrolledQualification;
import au.edu.tafesa.spsbuddyrestservice.model.StudentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Describes class that handles student data.
 *
 * @author Fedor Gabrus
 */
public interface StudentService {

    /**
     * Retrieves student with a particular id.
     *
     * @param id student's id
     * @return optional with a student with provided id or empty one
     */
    Optional<StudentDTO> getOneStudent(String id);

    /**
     * Retrieve a particular qualification that student is enrolled in.
     *
     * @param forStudentId student's id
     * @param qualificationCode qualification code
     * @return qualification that student enrolled in wrapped in optional
     */
    Optional<EnrolledQualification> getOneQualification(String forStudentId, String qualificationCode);

    /**
     * Retrieves list of student's qualifications.
     *
     * @param forStudentId student's id
     * @return list of student's qualifications or empty list.
     */
    List<EnrolledQualification> getAllQualifications(String forStudentId);

}
