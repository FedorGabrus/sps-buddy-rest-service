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

package au.edu.tafesa.spsbuddyrestservice.component.modelassembler;

import au.edu.tafesa.spsbuddyrestservice.entity.business.CompetencyQualification;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyForQualification;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides helper methods to convert CompetencyQualification entity into a representation model.
 */
@Component
public class CompetencyForQualificationModelAssemblerImpl implements CompetencyForQualificationModelAssembler {

    private final CompetencyModelAssembler competencyModelAssembler;
    private final CompetencyTypeModelAssembler competencyTypeModelAssembler;

    @Autowired
    public CompetencyForQualificationModelAssemblerImpl(CompetencyModelAssembler competencyModelAssembler,
                                                        CompetencyTypeModelAssembler competencyTypeModelAssembler) {
        this.competencyModelAssembler = competencyModelAssembler;
        this.competencyTypeModelAssembler = competencyTypeModelAssembler;
    }

    /**
     * Converts CompetencyQualification entity into a CompetencyForQualification representation model
     * @param competencyQualification CompetencyQualification entity. Not null
     * @return CompetencyForQualification representation model
     */
    @Override
    public CompetencyForQualification toModel(@NonNull CompetencyQualification competencyQualification) {
        return new CompetencyForQualification(competencyQualification.getCompetencyQualificationPK().getQualCode(),
                competencyModelAssembler.toModel(competencyQualification.getCompetency()),
                competencyTypeModelAssembler.toModel(competencyQualification.getCompetencyType()));
    }
}
