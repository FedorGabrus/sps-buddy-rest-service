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

import au.edu.tafesa.spsbuddyrestservice.entity.business.TermDateTime;
import au.edu.tafesa.spsbuddyrestservice.model.TermDateTimeDTO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Provides helper methods to convert TermDateTime entity into representation model.
 */
@Component
public class TermDateTimeModelAssemblerImpl implements TermDateTimeModelAssembler {

    /**
     * Converts TermDateTime entity into representation model.
     *
     * @param termDateTime term date and time. Not null
     * @return representation model
     */
    @Override
    public TermDateTimeDTO toModel(@NonNull TermDateTime termDateTime) {
        return new TermDateTimeDTO(termDateTime.getTermDatetimePK().getTermCode(),
                termDateTime.getTermDatetimePK().getTermYear(), termDateTime.getStartDate(), termDateTime.getEndDate(),
                termDateTime.getSemesterCode());
    }

}
