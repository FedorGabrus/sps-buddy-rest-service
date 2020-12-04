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

import au.edu.tafesa.spsbuddyrestservice.entity.business.StudentStudyPlan;
import au.edu.tafesa.spsbuddyrestservice.model.EnrolledQualification;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides helper methods to convert StudentStudyPlan entity into a representation model.
 */
@Component
public class EnrolledQualificationModelAssemblerImpl implements EnrolledQualificationModelAssembler {

    private final QualificationModelAssembler qualificationModelAssembler;
    private final TermDateTimeModelAssembler termDateTimeModelAssembler;

    @Autowired
    public EnrolledQualificationModelAssemblerImpl(QualificationModelAssembler qualificationModelAssembler,
                                                   TermDateTimeModelAssembler termDateTimeModelAssembler) {
        this.qualificationModelAssembler = qualificationModelAssembler;
        this.termDateTimeModelAssembler = termDateTimeModelAssembler;
    }

    /**
     * Converts StudentStudyPlan entity into a representation model.
     *
     * @param studentStudyPlan StudentStudyPlan entity. Not null
     * @return representation model
     */
    @Override
    public EnrolledQualification toModel(@NonNull StudentStudyPlan studentStudyPlan) {
        return new EnrolledQualification(
                studentStudyPlan.getStudentStudyPlanPK().getStudentID(),
                qualificationModelAssembler.toModel(studentStudyPlan.getQualification()),
                studentStudyPlan.getEnrolmentType(),
                termDateTimeModelAssembler.toModel(studentStudyPlan.getTermDateTime()));
    }

}
