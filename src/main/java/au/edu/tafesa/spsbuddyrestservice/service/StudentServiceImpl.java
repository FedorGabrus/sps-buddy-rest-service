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

import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.EnrolledQualificationModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.StudentModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.StudentStudyPlanPK;
import au.edu.tafesa.spsbuddyrestservice.exception.StudentNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.EnrolledQualification;
import au.edu.tafesa.spsbuddyrestservice.model.StudentDTO;
import au.edu.tafesa.spsbuddyrestservice.repository.business.EnrolledQualificationRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.business.StudentRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles student's data.
 *
 * @author Fedor Gabrus
 */
@Service
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    private final StudentModelAssembler studentModelAssembler;
    private final EnrolledQualificationRepository enrolledQualificationRepository;
    private final EnrolledQualificationModelAssembler enrolledQualificationModelAssembler;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              StudentModelAssembler studentModelAssembler,
                              EnrolledQualificationRepository enrolledQualificationRepository,
                              EnrolledQualificationModelAssembler enrolledQualificationModelAssembler) {
        this.studentRepository = studentRepository;
        this.studentModelAssembler = studentModelAssembler;
        this.enrolledQualificationRepository = enrolledQualificationRepository;
        this.enrolledQualificationModelAssembler = enrolledQualificationModelAssembler;
    }

    @Override
    public Optional<StudentDTO> getOneStudent(@NonNull String id) {
        return studentRepository.findById(id).map(studentModelAssembler::toModel);
    }

    /**
     * Retrieve a particular qualification that student is enrolled in.
     *
     * @param forStudentId      student's id. Not null
     * @param qualificationCode qualification code. Not null
     * @return qualification that student enrolled in wrapped in optional
     */
    @Override
    public Optional<EnrolledQualification> getOneQualification(@NonNull String forStudentId,
                                                               @NonNull String qualificationCode) {
        return enrolledQualificationRepository.findById(new StudentStudyPlanPK(forStudentId, qualificationCode))
                .map(enrolledQualificationModelAssembler::toModel);
    }

    /**
     * Retrieves list of student's qualifications.
     *
     * @param forStudentId student's id. Not null
     * @return list of student's qualifications or empty list
     * @throws StudentNotFoundException if student not found
     */
    @Override
    public List<EnrolledQualification> getAllQualifications(@NonNull String forStudentId)
            throws StudentNotFoundException {
        return studentRepository.findById(forStudentId).orElseThrow(StudentNotFoundException::new)
                .getStudentQualifications().stream()
                .map(enrolledQualificationModelAssembler::toModel)
                .collect(Collectors.toList());
    }

}
