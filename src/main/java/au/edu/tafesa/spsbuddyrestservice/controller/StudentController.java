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
package au.edu.tafesa.spsbuddyrestservice.controller;

import au.edu.tafesa.spsbuddyrestservice.component.EndPointAccess;
import au.edu.tafesa.spsbuddyrestservice.exception.QualificationNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.StudentNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.EnrolledQualification;
import au.edu.tafesa.spsbuddyrestservice.model.StudentDTO;
import au.edu.tafesa.spsbuddyrestservice.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Students end-points.
 *
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final EndPointAccess endPointAccess;

    @Autowired
    public StudentController(StudentService studentService, EndPointAccess endPointAccess) {
        this.studentService = studentService;
        this.endPointAccess = endPointAccess;
    }

    /**
     * Retrieves student with a particular id.
     * Returns 404 if student not found.
     *
     * @param id student's id
     * @return student with provided id if found
     */
    @GetMapping("/{id}")
    public StudentDTO getOneStudent(@PathVariable String id) {
        // If user is a student, they can access only their own data.
        endPointAccess.throw403IfUserIsStudentAndHasAnotherId(id);

        // Finds a student or throws exception if no student with provided id.
        return studentService.getOneStudent(id).orElseThrow(StudentNotFoundException::new);
    }

    /**
     * Retrieves list of qualifications for a particular student.
     * Returns 404 if student not found or if student doesn't have any qualifications
     *
     * @param forStudentId student's id
     * @return Collection Model of student's qualifications
     */
    @GetMapping("/{forStudentId}/qualifications")
    public CollectionModel<EnrolledQualification> getAllQualifications(@PathVariable String forStudentId) {
        // Student can access only their own data.
        endPointAccess.throw403IfUserIsStudentAndHasAnotherId(forStudentId);

        // Get list of student's qualifications.
        final List<EnrolledQualification> studentQualifications = studentService.getAllQualifications(forStudentId);
        // Returns 404 if no qualifications.
        if (studentQualifications.isEmpty()) {
            throw new QualificationNotFoundException("Student is not enrolled in any qualifications.");
        }

        return CollectionModel.of(studentQualifications,
                linkTo(methodOn(StudentController.class).getAllQualifications(forStudentId)).withSelfRel());
    }

    /**
     * Retrieves a particular qualification with related enrolment student's data.
     * Returns 404 if student is not enrolled in particular qualification.
     *
     * @param forStudentId student's ID
     * @param qualificationCode qualification code
     * @return qualification with enrolment related data
     */
    @GetMapping("/{forStudentId}/qualifications/{qualificationCode}")
    public EnrolledQualification getOneQualification(@PathVariable String forStudentId,
                                                     @PathVariable String qualificationCode) {
        // Student can access only their own data.
        endPointAccess.throw403IfUserIsStudentAndHasAnotherId(forStudentId);

        return studentService.getOneQualification(forStudentId, qualificationCode)
                .orElseThrow(() -> new QualificationNotFoundException("Student is not enrolled in this qualification"));
    }

}
