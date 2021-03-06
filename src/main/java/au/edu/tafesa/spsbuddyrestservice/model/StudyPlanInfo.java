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
import au.edu.tafesa.spsbuddyrestservice.controller.StudyPlanController;
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
 * Used to transfer {@link au.edu.tafesa.spsbuddyrestservice.entity.business.StudyPlanQualification} data.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "studyPlans", itemRelation = "studyPlan")
public class StudyPlanInfo extends RepresentationModel<StudyPlanInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String studyPlanCode;
    @JsonIgnore
    private final String qualificationCode;
    private final int priority;

    public StudyPlanInfo(@NonNull String studyPlanCode, @NonNull String qualificationCode, int priority) {
        super(Arrays.asList(
                linkTo(methodOn(StudyPlanController.class).getOneStudyPlanInfo(studyPlanCode)).withSelfRel(),
                linkTo(methodOn(StudyPlanController.class).getAllSubjects(studyPlanCode)).withRel("studyPlanSubjects"),
                linkTo(methodOn(QualificationController.class).getOneQualification(qualificationCode))
                        .withRel("qualification")
        ));

        this.studyPlanCode = studyPlanCode;
        this.qualificationCode = qualificationCode;
        this.priority = priority;
    }

}
