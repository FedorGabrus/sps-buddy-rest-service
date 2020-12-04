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

import au.edu.tafesa.spsbuddyrestservice.controller.CompetencyController;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Used to transfer competency data.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "competencies", itemRelation = "competency")
public class CompetencyDTO extends RepresentationModel<CompetencyDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String tafeCompCode;
    private final String nationalCompCode;
    private final String competencyName;
    private final int hours;

    public CompetencyDTO(@NonNull String tafeCompCode, @NonNull String nationalCompCode,
                         @NonNull String competencyName, int hours) {
        super(linkTo(methodOn(CompetencyController.class).getOneCompetency(tafeCompCode)).withSelfRel());

        this.tafeCompCode = tafeCompCode;
        this.nationalCompCode = nationalCompCode;
        this.competencyName = competencyName;
        this.hours = hours;
    }

}
