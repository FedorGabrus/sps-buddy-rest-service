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

import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.CompetencyModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyDTO;
import au.edu.tafesa.spsbuddyrestservice.repository.business.CompetencyRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Handles competency related data and operations.
 *
 * @author Fedor Gabrus
 */
@Service
public class CompetencyServiceImpl implements CompetencyService {

    private final CompetencyRepository competencyRepository;
    private final CompetencyModelAssembler competencyModelAssembler;

    @Autowired
    public CompetencyServiceImpl(CompetencyRepository competencyRepository,
                                 CompetencyModelAssembler competencyModelAssembler) {
        this.competencyRepository = competencyRepository;
        this.competencyModelAssembler = competencyModelAssembler;
    }

    /**
     * Retrieves a competency with a particular TAFE code.
     *
     * @param forTafeCode competency TAFE code. Not null
     * @return competency representation model wrapped in optional
     */
    @Override
    public Optional<CompetencyDTO> getOneCompetency(@NonNull String forTafeCode) {
        return  competencyRepository.findById(forTafeCode).map(competencyModelAssembler::toModel);
    }

}
