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

import au.edu.tafesa.spsbuddyrestservice.exception.CompetencyNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.QualificationNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.SubjectNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyForQualification;
import au.edu.tafesa.spsbuddyrestservice.model.QualificationDTO;
import au.edu.tafesa.spsbuddyrestservice.model.SubjectForQualification;

import java.util.List;
import java.util.Optional;

/**
 * Describes class that handles qualification data.
 *
 * @author Fedor Gabrus
 */
public interface QualificationService {

    /**
     * Retrieves qualification with a particular qualification code.
     *
     * @param qualificationCode qualification code
     * @return qualification data wrapped in optional
     */
    Optional<QualificationDTO> getOneQualification(String qualificationCode);

    /**
     * Retrieves list of all qualifications.
     *
     * @return list of qualifications
     */
    List<QualificationDTO> getAllQualifications();

    /**
     * Retrieves competencies with related to qualification data.
     *
     * @param forQualificationCode qualification code
     * @return List of competencies with related to qualification data
     * @throws QualificationNotFoundException throws when no qualification with provided qualification code
     */
    List<CompetencyForQualification> getAllCompetencies(String forQualificationCode) throws QualificationNotFoundException;

    /**
     * Retrieves a particular competency with a related data to a particular qualification.
     *
     * @param forQualificationCode qualification code
     * @param competencyTafeCode competency TAFE code
     * @return competency with a related data to the qualification
     * @throws CompetencyNotFoundException if no competency with provided TAFE code
     */
    CompetencyForQualification getOneCompetency(String forQualificationCode, String competencyTafeCode)
            throws CompetencyNotFoundException;

    /**
     * Retrieves list of subjects for a particular qualification.
     *
     * @param forQualificationCode qualification code
     * @return list of subjects for a particular qualification
     * @throws QualificationNotFoundException throws if no qualification with provided code
     */
    List<SubjectForQualification> getAllSubjects(String forQualificationCode) throws QualificationNotFoundException;

    /**
     * Retrieves a subject with qualification related data.
     *
     * @param forQualificationCode qualification code
     * @param subjectCode subject code
     * @return subject with qualification related data
     * @throws SubjectNotFoundException throws when no subject found
     */
    SubjectForQualification getOneSubject(String forQualificationCode, String subjectCode) throws SubjectNotFoundException;

}
