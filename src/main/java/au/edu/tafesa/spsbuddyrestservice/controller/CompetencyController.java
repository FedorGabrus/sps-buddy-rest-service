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

import au.edu.tafesa.spsbuddyrestservice.exception.CompetencyNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyDTO;
import au.edu.tafesa.spsbuddyrestservice.service.CompetencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Competency related end-points.
 *
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/competencies")
public class CompetencyController {

    private final CompetencyService competencyService;

    @Autowired
    public CompetencyController(CompetencyService competencyService) {
        this.competencyService = competencyService;
    }

    /**
     * Returns competency with a particular TAFE code.
     * Returns 404 if no competency with provided code.
     *
     * @param tafeCode competency TAFE code
     * @return competency representation model
     */
    @GetMapping("/{tafeCode}")
    public CompetencyDTO getOneCompetency(@PathVariable String tafeCode) {
        return competencyService.getOneCompetency(tafeCode).orElseThrow(CompetencyNotFoundException::new);
    }

}
