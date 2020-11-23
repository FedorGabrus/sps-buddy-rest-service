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

import au.edu.tafesa.spsbuddyrestservice.entity.business.SubjectQualification;
import au.edu.tafesa.spsbuddyrestservice.model.SubjectForQualification;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides helper methods to convert SubjectQualification entity into a representation model.
 */
@Component
public class SubjectForQualificationModelAssemblerImpl implements SubjectForQualificationModelAssembler {

    private final SubjectModelAssembler subjectModelAssembler;

    @Autowired
    public SubjectForQualificationModelAssemblerImpl(SubjectModelAssembler subjectModelAssembler) {
        this.subjectModelAssembler = subjectModelAssembler;
    }

    /**
     * Converts a {@link SubjectQualification} entity into a {@link SubjectForQualification}.
     *
     * @param subjectQualification SubjectQualification entity. Not null
     * @return SubjectForQualification representation model
     */
    @Override
    public SubjectForQualification toModel(@NonNull SubjectQualification subjectQualification) {
        return new SubjectForQualification(subjectQualification.getSubjectQualificationPK().getQualCode(),
                subjectModelAssembler.toModel(subjectQualification.getSubject()), subjectQualification.getUsageType());
    }
}
