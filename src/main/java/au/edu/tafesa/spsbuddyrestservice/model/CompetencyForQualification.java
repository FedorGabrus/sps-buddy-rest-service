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
 * Used to transfer competency data with relation to qualification.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "qualificationCompetencies", itemRelation = "qualificationCompetency")
public class CompetencyForQualification extends RepresentationModel<CompetencyForQualification> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private final String qualificationCode;

    private final CompetencyDTO competency;
    private final CompetencyTypeDTO competencyType;

    public CompetencyForQualification(@NonNull String qualificationCode, @NonNull CompetencyDTO competency,
                                      @NonNull CompetencyTypeDTO competencyType) {
        super(Arrays.asList(
                linkTo(methodOn(QualificationController.class)
                        .getOneCompetency(qualificationCode, competency.getTafeCompCode())).withSelfRel(),
                linkTo(methodOn(QualificationController.class)
                        .getAllCompetencies(qualificationCode)).withRel("qualificationCompetencies"),
                linkTo(methodOn(QualificationController.class)
                        .getOneQualification(qualificationCode)).withRel("qualification")
        ));

        this.qualificationCode = qualificationCode;
        this.competency = competency;
        this.competencyType = competencyType;
    }
}
