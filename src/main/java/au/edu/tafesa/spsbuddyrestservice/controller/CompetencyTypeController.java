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

import au.edu.tafesa.spsbuddyrestservice.exception.CompetencyTypeNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyTypeDTO;
import au.edu.tafesa.spsbuddyrestservice.service.CompetencyTypeService;
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
 * Competency Type related end-points.
 *
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/competency-types")
public class CompetencyTypeController {

    private final CompetencyTypeService competencyTypeService;

    @Autowired
    public CompetencyTypeController(CompetencyTypeService competencyTypeService) {
        this.competencyTypeService = competencyTypeService;
    }

    /**
     * Retrieves competency type with a particular type code.
     * Returns 404 if competency not found.
     *
     * @param competencyTypeCode competency type code
     * @return competency type representation model
     */
    @GetMapping("/{competencyTypeCode}")
    public CompetencyTypeDTO getOneCompetencyType(@PathVariable String competencyTypeCode) {
        return competencyTypeService.getOneCompetencyType(competencyTypeCode)
                .orElseThrow(CompetencyTypeNotFoundException::new);
    }

    /**
     * Retrieves all competency types.
     * Returns 404 if non found.
     *
     * @return list of competency types
     */
    @GetMapping
    public CollectionModel<CompetencyTypeDTO> getAllCompetencyTypes() {
        final List<CompetencyTypeDTO> competencyTypes = competencyTypeService.getAllCompetencyTypes();
        if (competencyTypes.isEmpty()) {
            throw new CompetencyTypeNotFoundException();
        }
        return CollectionModel.of(competencyTypes,
                linkTo(methodOn(CompetencyTypeController.class).getAllCompetencyTypes()).withSelfRel());
    }

}
