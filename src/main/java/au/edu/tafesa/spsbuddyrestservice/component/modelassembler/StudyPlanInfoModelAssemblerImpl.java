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

import au.edu.tafesa.spsbuddyrestservice.entity.business.StudyPlanQualification;
import au.edu.tafesa.spsbuddyrestservice.model.StudyPlanInfo;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Provides helper methods to convert {@link StudyPlanQualification} entity into a {@link StudyPlanInfo}.
 */
@Component
public class StudyPlanInfoModelAssemblerImpl implements StudyPlanInfoModelAssembler {

    /**
     * Converts {@link StudyPlanQualification} into a {@link StudyPlanInfo}
     * @param studyPlanQualification StudyPlanQualification entity. Not null
     * @return StudyPlanInfo representation model
     */
    @Override
    public StudyPlanInfo toModel(@NonNull StudyPlanQualification studyPlanQualification) {
        return new StudyPlanInfo(
                studyPlanQualification.getStudyPlanCode(),
                studyPlanQualification.getQualificationCode(),
                studyPlanQualification.getPriority()
        );
    }
}
