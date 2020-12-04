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

import au.edu.tafesa.spsbuddyrestservice.entity.business.Qualification;
import au.edu.tafesa.spsbuddyrestservice.model.QualificationDTO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Converts Qualification entity into representation model.
 */
@Component
public class QualificationModelAssemblerImpl implements QualificationModelAssembler {

    /**
     * Converts Qualification entity into representation model.
     *
     * @param qualification qualification. Not null
     * @return qualification representation model
     */
    @Override
    public QualificationDTO toModel(@NonNull Qualification qualification) {
        return new QualificationDTO(qualification.getQualCode(), qualification.getNationalQualCode(),
                qualification.getTafeQualCode(), qualification.getQualName(), qualification.getTotalUnits(),
                qualification.getCoreUnits(), qualification.getElectedUnits(), qualification.getReqListedElectedUnits());
    }

}
