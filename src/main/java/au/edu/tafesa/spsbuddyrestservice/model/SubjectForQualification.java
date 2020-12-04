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

import au.edu.tafesa.spsbuddyrestservice.controller.QualificationController;
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
 * Used to transfer SubjectQualification entity related data.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "qualificationSubjects", itemRelation = "qualificationSubject")
public class SubjectForQualification extends RepresentationModel<SubjectForQualification> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private final String qualificationCode;
    private final SubjectDTO subject;
    private final String usageType;

    public SubjectForQualification(@NonNull String qualificationCode, @NonNull SubjectDTO subject, String usageType) {
        super(Arrays.asList(
                linkTo(methodOn(QualificationController.class)
                        .getOneSubject(qualificationCode, subject.getSubjectCode())).withSelfRel(),
                linkTo(methodOn(QualificationController.class)
                        .getAllSubjects(qualificationCode)).withRel("qualificationSubjects"),
                linkTo(methodOn(QualificationController.class)
                        .getOneQualification(qualificationCode)).withRel("qualification")
        ));

        this.qualificationCode = qualificationCode;
        this.subject = subject;
        this.usageType = usageType;
    }

}
