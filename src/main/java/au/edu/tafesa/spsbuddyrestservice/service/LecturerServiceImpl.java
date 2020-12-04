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

import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.LecturerModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.model.LecturerDTO;
import au.edu.tafesa.spsbuddyrestservice.repository.business.LecturerRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Handles lecturer related data.
 */
@Service
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final LecturerModelAssembler lecturerModelAssembler;

    @Autowired
    public LecturerServiceImpl(LecturerRepository lecturerRepository, LecturerModelAssembler lecturerModelAssembler) {
        this.lecturerRepository = lecturerRepository;
        this.lecturerModelAssembler = lecturerModelAssembler;
    }

    /**
     * Retrieves data for a lecturer with a particular id.
     *
     * @param lecturerId lecturer's id. Not null
     * @return lecturer's data if found wrapped in optional
     */
    @Override
    public Optional<LecturerDTO> getOneLecturer(@NonNull String lecturerId) {
        return lecturerRepository.findById(lecturerId)
                .map(lecturerModelAssembler::toModel);
    }

}
