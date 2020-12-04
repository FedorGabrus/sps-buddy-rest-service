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


import au.edu.tafesa.spsbuddyrestservice.controller.StudentController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Used to transfer student's specific qualification data.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "studentQualifications", itemRelation = "studentQualification")
public class EnrolledQualification extends RepresentationModel<EnrolledQualification>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private final String studentId;

    private final QualificationDTO qualification;
    private final String enrolmentType;
    private final TermDateTimeDTO termDateTime;

    public EnrolledQualification(@NonNull String studentId, @NonNull QualificationDTO qualification,
                                 @NonNull String enrolmentType, @NonNull TermDateTimeDTO termDateTime) {
        super(Arrays.asList(
                linkTo(methodOn(StudentController.class)
                        .getOneQualification(studentId, qualification.getQualificationCode())).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllQualifications(studentId))
                        .withRel("studentQualifications"),
                linkTo(methodOn(StudentController.class).getOneStudent(studentId)).withRel("student")));

        this.studentId = studentId;
        this.qualification = qualification;
        this.enrolmentType = enrolmentType;
        this.termDateTime = termDateTime;
    }
}
