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

import au.edu.tafesa.spsbuddyrestservice.controller.CompetencyTypeController;
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
 * Used to transfer Competency Type data.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "competencyTypes", itemRelation = "competencyType")
public class CompetencyTypeDTO extends RepresentationModel<CompetencyTypeDTO> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Link linkToAllCompetencyTypes = linkTo(methodOn(CompetencyTypeController.class)
            .getAllCompetencyTypes()).withRel("competencyTypes");

    private final String compTypeCode;
    private final String compTypeDescription;

    public CompetencyTypeDTO(@NonNull String compTypeCode, @NonNull String compTypeDescription) {
        super(Arrays.asList(
                linkTo(methodOn(CompetencyTypeController.class).getOneCompetencyType(compTypeCode)).withSelfRel(),
                linkToAllCompetencyTypes
        ));
        this.compTypeCode = compTypeCode;
        this.compTypeDescription = compTypeDescription;
    }
}
