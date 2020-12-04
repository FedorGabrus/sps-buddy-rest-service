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

import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.SubjectModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.model.SubjectDTO;
import au.edu.tafesa.spsbuddyrestservice.repository.business.SubjectRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Processes subject related data.
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectModelAssembler subjectModelAssembler;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectModelAssembler subjectModelAssembler) {
        this.subjectRepository = subjectRepository;
        this.subjectModelAssembler = subjectModelAssembler;
    }

    /**
     * Retrieves a subject with a particular subject code.
     *
     * @param subjectCode subject code. Not null
     * @return subject representation model
     */
    @Override
    public Optional<SubjectDTO> getOneSubject(@NonNull String subjectCode) {
        return subjectRepository.findById(subjectCode).map(subjectModelAssembler::toModel);
    }

}
