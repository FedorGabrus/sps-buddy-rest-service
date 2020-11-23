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

import au.edu.tafesa.spsbuddyrestservice.entity.business.CompetencyType;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyTypeDTO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Provides helper methods to convert CompetencyType entity into a representation model.
 */
@Component
public class CompetencyTypeModelAssemblerImpl implements CompetencyTypeModelAssembler {

    /**
     * Converts CompetencyType entity into a representation model.
     *
     * @param competencyType competency type entity. Not null
     * @return competency type representation model
     */
    @Override
    public CompetencyTypeDTO toModel(@NonNull CompetencyType competencyType) {
        return new CompetencyTypeDTO(competencyType.getCompTypeCode(), competencyType.getCompTypeDescription());
    }
}
