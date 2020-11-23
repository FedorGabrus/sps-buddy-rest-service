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

import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.CompetencyForQualificationModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.QualificationModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.SubjectForQualificationModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.CompetencyQualificationPK;
import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.SubjectQualificationPK;
import au.edu.tafesa.spsbuddyrestservice.exception.CompetencyNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.QualificationNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.SubjectNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyForQualification;
import au.edu.tafesa.spsbuddyrestservice.model.QualificationDTO;
import au.edu.tafesa.spsbuddyrestservice.model.SubjectForQualification;
import au.edu.tafesa.spsbuddyrestservice.repository.business.CompetencyQualificationRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.business.CompetencyRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.business.QualificationRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.business.SubjectQualificationRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles qualification data and related operations.
 *
 * @author Fedor Gabrus
 */
@Service
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;
    private final QualificationModelAssembler qualificationModelAssembler;
    private final CompetencyForQualificationModelAssembler competencyForQualificationModelAssembler;
    private final CompetencyRepository competencyRepository;
    private final CompetencyQualificationRepository competencyQualificationRepository;
    private final SubjectForQualificationModelAssembler subjectForQualificationModelAssembler;
    private final SubjectQualificationRepository subjectQualificationRepository;

    @Autowired
    public QualificationServiceImpl(QualificationRepository qualificationRepository,
                                    QualificationModelAssembler qualificationModelAssembler,
                                    CompetencyForQualificationModelAssembler competencyForQualificationModelAssembler,
                                    CompetencyRepository competencyRepository,
                                    CompetencyQualificationRepository competencyQualificationRepository,
                                    SubjectForQualificationModelAssembler subjectForQualificationModelAssembler,
                                    SubjectQualificationRepository subjectQualificationRepository) {
        this.qualificationRepository = qualificationRepository;
        this.qualificationModelAssembler = qualificationModelAssembler;
        this.competencyForQualificationModelAssembler = competencyForQualificationModelAssembler;
        this.competencyRepository = competencyRepository;
        this.competencyQualificationRepository = competencyQualificationRepository;
        this.subjectForQualificationModelAssembler = subjectForQualificationModelAssembler;
        this.subjectQualificationRepository = subjectQualificationRepository;
    }

    /**
     * Retrieves qualification with a particular qualification code.
     *
     * @param qualificationCode qualification code. Not null
     * @return qualification data wrapped in optional
     */
    @Override
    public Optional<QualificationDTO> getOneQualification(@NonNull String qualificationCode) {
        return qualificationRepository.findById(qualificationCode)
                .map(qualificationModelAssembler::toModel);
    }

    /**
     * Retrieves list of all qualifications.
     *
     * @return list of qualifications
     */
    @Override
    public List<QualificationDTO> getAllQualifications() {
        final var qualifications = new ArrayList<QualificationDTO>();
        qualificationRepository.findAll().forEach(q -> qualifications.add(qualificationModelAssembler.toModel(q)));
        return qualifications;
    }

    /**
     * Retrieves competencies with related to qualification data.
     *
     * @param forQualificationCode qualification code. Not null
     * @return List of competencies with related to qualification data
     * @throws QualificationNotFoundException throws when no qualification with provided qualification code
     */
    @Override
    public List<CompetencyForQualification> getAllCompetencies(@NonNull String forQualificationCode)
            throws QualificationNotFoundException {
        return qualificationRepository.findById(forQualificationCode)
                .orElseThrow(QualificationNotFoundException::new)
                .getCompetencyQualifications().stream()
                .map(competencyForQualificationModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a particular competency with a related data to a particular qualification.
     *
     * @param forQualificationCode qualification code. Not null
     * @param competencyTafeCode   competency TAFE code. Not null
     * @return competency with a related data to the qualification
     * @throws CompetencyNotFoundException if no proper competency found
     */
    @Override
    public CompetencyForQualification getOneCompetency(@NonNull String forQualificationCode,
                                                                 @NonNull String competencyTafeCode)
            throws CompetencyNotFoundException {
        // Inefficient logic due to DB structure that prevents from proper querying.
        return competencyForQualificationModelAssembler.toModel(
                competencyQualificationRepository.findById(
                        new CompetencyQualificationPK(
                                forQualificationCode,
                                competencyRepository.findById(competencyTafeCode)
                                        .orElseThrow(CompetencyNotFoundException::new).getNationalCompCode()
                        )
                ).orElseThrow(CompetencyNotFoundException::new)
        );
    }

    /**
     * Retrieves list of subjects for a particular qualification.
     *
     * @param forQualificationCode qualification code. Not null
     * @return list of subjects for a particular qualification
     * @throws QualificationNotFoundException throws if no qualification with provided code
     */
    @Override
    public List<SubjectForQualification> getAllSubjects(@NonNull String forQualificationCode)
            throws QualificationNotFoundException {
        return qualificationRepository.findById(forQualificationCode).orElseThrow(QualificationNotFoundException::new)
                .getSubjectQualifications().stream()
                .map(subjectForQualificationModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a subject with qualification related data.
     * <p>Returns 404 if no subject for qualification found</p>
     *
     * @param forQualificationCode qualification code. Not null
     * @param subjectCode          subject code. Not null
     * @return subject with qualification related data
     * @throws SubjectNotFoundException throws when no subject found
     */
    @Override
    public SubjectForQualification getOneSubject(@NonNull String forQualificationCode, @NonNull String subjectCode)
            throws SubjectNotFoundException {
        return subjectForQualificationModelAssembler.toModel(
                subjectQualificationRepository.findById(new SubjectQualificationPK(forQualificationCode, subjectCode))
                        .orElseThrow(SubjectNotFoundException::new)
        );
    }

}
