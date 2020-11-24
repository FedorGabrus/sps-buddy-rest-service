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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Used to transfer qualification data.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "qualifications", itemRelation = "qualification")
public class QualificationDTO extends RepresentationModel<QualificationDTO> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Link linkToQualifications = linkTo(methodOn(QualificationController.class)
            .getAllQualifications()).withRel("qualifications");

    private final String qualificationCode;
    private final String nationalCode;
    private final String tafeCode;
    private final String name;
    private final int totalUnits;
    private final int coreUnits;
    private final int electedUnits;
    private final int reqListedElectedUnits;

    public QualificationDTO(@NonNull String qualificationCode, @NonNull String nationalCode,
                            @NonNull String tafeCode, @NonNull String name, int totalUnits, int coreUnits,
                            int electedUnits, int reqListedElectedUnits) {
        super(Arrays.asList(
                linkTo(methodOn(QualificationController.class).getOneQualification(qualificationCode)).withSelfRel(),
                linkToQualifications,
                linkTo(methodOn(QualificationController.class).getAllCompetencies(qualificationCode))
                        .withRel("qualificationCompetencies"),
                linkTo(methodOn(QualificationController.class).getAllSubjects(qualificationCode))
                        .withRel("qualificationSubjects"),
                linkTo(methodOn(QualificationController.class).getAllStudyPlansInfo(qualificationCode))
                        .withRel("qualificationStudyPlans")
        ));

        this.qualificationCode = qualificationCode;
        this.nationalCode = nationalCode;
        this.tafeCode = tafeCode;
        this.name = name;
        this.totalUnits = totalUnits;
        this.coreUnits = coreUnits;
        this.electedUnits = electedUnits;
        this.reqListedElectedUnits = reqListedElectedUnits;
    }
}
