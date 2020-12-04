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

import au.edu.tafesa.spsbuddyrestservice.entity.business.Student;
import au.edu.tafesa.spsbuddyrestservice.model.StudentDTO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Converts Student entity into representation model.
 *
 * @author Fedor Gabrus
 */
@Component
public class StudentModelAssemblerImpl implements StudentModelAssembler {

    /**
     * Converts Student entity into representation model.
     *
     * @param student student entity. Not null
     * @return student representation model
     */
    @Override
    public StudentDTO toModel(@NonNull Student student) {
        return new StudentDTO(student.getStudentID(), student.getFirstName(), student.getLastName(),
                student.getEmailAddress());
    }

}
