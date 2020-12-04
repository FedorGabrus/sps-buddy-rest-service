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

import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.CompetencyTypeModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyTypeDTO;
import au.edu.tafesa.spsbuddyrestservice.repository.business.CompetencyTypeRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Processes competency type related operations.
 */
@Service
public class CompetencyTypeServiceImpl implements CompetencyTypeService {

    private final CompetencyTypeModelAssembler competencyTypeModelAssembler;
    private final CompetencyTypeRepository competencyTypeRepository;

    @Autowired
    public CompetencyTypeServiceImpl(CompetencyTypeModelAssembler competencyTypeModelAssembler,
                                     CompetencyTypeRepository competencyTypeRepository) {
        this.competencyTypeModelAssembler = competencyTypeModelAssembler;
        this.competencyTypeRepository = competencyTypeRepository;
    }

    /**
     * Retrieves competency type with a particular code.
     *
     * @param competencyTypeCode competency type code. Not null
     * @return competency type representation model wrapped in optional
     */
    @Override
    public Optional<CompetencyTypeDTO> getOneCompetencyType(@NonNull String competencyTypeCode) {
        return competencyTypeRepository.findById(competencyTypeCode).map(competencyTypeModelAssembler::toModel);
    }

    /**
     * Retrieves list of all competency types.
     *
     * @return list of all competency type representation models
     */
    @Override
    public List<CompetencyTypeDTO> getAllCompetencyTypes() {
        return StreamSupport.stream(competencyTypeRepository.findAll().spliterator(), false)
                .map(competencyTypeModelAssembler::toModel)
                .collect(Collectors.toList());
    }
}
