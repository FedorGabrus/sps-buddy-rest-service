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

import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.StudyPlanInfoModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.model.StudyPlanInfo;
import au.edu.tafesa.spsbuddyrestservice.repository.business.StudyPlanQualificationRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Processes study plan related data.
 */
@Service
public class StudyPlanServiceImpl implements StudyPlanService {

    private final StudyPlanQualificationRepository studyPlanQualificationRepository;
    private final StudyPlanInfoModelAssembler studyPlanInfoModelAssembler;

    @Autowired
    public StudyPlanServiceImpl(StudyPlanQualificationRepository studyPlanQualificationRepository,
                                StudyPlanInfoModelAssembler studyPlanInfoModelAssembler) {
        this.studyPlanQualificationRepository = studyPlanQualificationRepository;
        this.studyPlanInfoModelAssembler = studyPlanInfoModelAssembler;
    }

    /**
     * Retrieves study plan info with a particular code.
     *
     * @param studyPlanCode study plan code. Not null
     * @return study plan info with a particular code
     */
    @Override
    public Optional<StudyPlanInfo> getOneStudyPlanInfo(@NonNull String studyPlanCode) {
         return  studyPlanQualificationRepository.findById(studyPlanCode).map(studyPlanInfoModelAssembler::toModel);
    }
}
