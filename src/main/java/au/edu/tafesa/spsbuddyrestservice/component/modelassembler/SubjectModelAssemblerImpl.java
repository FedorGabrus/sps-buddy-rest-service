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

import au.edu.tafesa.spsbuddyrestservice.entity.business.Subject;
import au.edu.tafesa.spsbuddyrestservice.model.SubjectDTO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Provides helper methods to convert Subject entity into representation model.
 */
@Component
public class SubjectModelAssemblerImpl implements SubjectModelAssembler {

    /**
     * Converts subject entity into representation model
     * @param subject subject entity
     * @return subject representation model
     */
    @Override
    public SubjectDTO toModel(@NonNull Subject subject) {
        return new SubjectDTO(subject.getSubjectCode(), subject.getSubjectDescription(),
                subject.getPrerequisites().stream().map(this::toModel).collect(Collectors.toList())) ;
    }
}
