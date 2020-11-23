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

import au.edu.tafesa.spsbuddyrestservice.entity.business.Lecturer;
import au.edu.tafesa.spsbuddyrestservice.model.LecturerDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * Converts lecturer into representation model.
 *
 * @author Fedor Gabrus
 */
@Service
public class LecturerModelAssemblerImpl implements LecturerModelAssembler {

    /**
     * Converts lecturer entity into a representation model
     * @param lecturer lecturer entity. Not null
     * @return representation model
     */
    @Override
    public LecturerDTO toModel(@NonNull Lecturer lecturer) {
        return new LecturerDTO(lecturer.getLecturerID(), lecturer.getFirstName(), lecturer.getLastName(),
                lecturer.getEmailAddress());
    }

}
