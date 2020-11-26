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
import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.SubjectModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.entity.business.StudyPlanSubject;
import au.edu.tafesa.spsbuddyrestservice.entity.business.Subject;
import au.edu.tafesa.spsbuddyrestservice.entity.business.SubjectCompetency;
import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.StudyPlanSubjectPK;
import au.edu.tafesa.spsbuddyrestservice.exception.CompetencyNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.StudyPlanNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.SubjectNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyForQualification;
import au.edu.tafesa.spsbuddyrestservice.model.StudyPlanInfo;
import au.edu.tafesa.spsbuddyrestservice.model.SubjectForStudyPlan;
import au.edu.tafesa.spsbuddyrestservice.repository.business.StudyPlanQualificationRepository;
import au.edu.tafesa.spsbuddyrestservice.repository.business.StudyPlanSubjectRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Processes study plan related data.
 */
@Slf4j
@Service
public class StudyPlanServiceImpl implements StudyPlanService {

    private final StudyPlanQualificationRepository studyPlanQualificationRepository;
    private final StudyPlanInfoModelAssembler studyPlanInfoModelAssembler;
    private final StudyPlanSubjectRepository studyPlanSubjectRepository;
    private final QualificationService qualificationService;
    private final SubjectModelAssembler subjectModelAssembler;

    @Autowired
    public StudyPlanServiceImpl(StudyPlanQualificationRepository studyPlanQualificationRepository,
                                StudyPlanInfoModelAssembler studyPlanInfoModelAssembler,
                                StudyPlanSubjectRepository studyPlanSubjectRepository,
                                QualificationService qualificationService,
                                SubjectModelAssembler subjectModelAssembler) {
        this.studyPlanQualificationRepository = studyPlanQualificationRepository;
        this.studyPlanInfoModelAssembler = studyPlanInfoModelAssembler;
        this.studyPlanSubjectRepository = studyPlanSubjectRepository;
        this.qualificationService = qualificationService;
        this.subjectModelAssembler = subjectModelAssembler;
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

    /**
     * Retrieves a particular subject with study plan related data.
     *
     * @param forStudyPlanCode study plan code. Not null
     * @param subjectCode      subject code. Not null
     * @return particular subject with study plan related data
     * @throws StudyPlanNotFoundException throws if no study plan with provided id
     * @throws SubjectNotFoundException throws if the subject is not related to study plan
     */
    @Override
    public SubjectForStudyPlan getOneSubject(@NonNull String forStudyPlanCode, @NonNull String subjectCode)
            throws StudyPlanNotFoundException, SubjectNotFoundException {
        // Retrieves study plan to obtain qualification code.
        final String qualificationCode = studyPlanQualificationRepository.findById(forStudyPlanCode)
                .orElseThrow(StudyPlanNotFoundException::new).getQualificationCode();
        // Retrieves StudyPlanSubject entity to get timing stage and term.
        final StudyPlanSubject studyPlanSubject = studyPlanSubjectRepository.findById(
                new StudyPlanSubjectPK(forStudyPlanCode, subjectCode)
        ).orElseThrow(SubjectNotFoundException::new);
        // Gets subject.
        final Subject subject = studyPlanSubject.getSubject();
        // Gets subject's competencies.
        final List<SubjectCompetency> subjectCompetencies = subject.getSubjectCompetencies();

        // Retrieves qualification specific data for each competency.
        final List<CompetencyForQualification> competencyForQualifications = new ArrayList<>();
        subjectCompetencies.forEach(sc -> {
            // Adds competency to the list if competency related to the qualification
            try {
                competencyForQualifications.add(qualificationService.getOneCompetency(
                        qualificationCode,
                        sc.getSubjectCompetencyPK().getTafeCompCode()
                ));
            }
            catch (CompetencyNotFoundException e) {
                log.debug(sc.getSubjectCompetencyPK().getTafeCompCode() + " competency for subject "
                + sc.getSubjectCompetencyPK().getSubjectCode() + " for Study Plan " + forStudyPlanCode
                + " doesn't belong to qualification " + qualificationCode);
            }
        });

        if (competencyForQualifications.isEmpty()) {
            log.error("Subject " + subjectCode + " in Study Plan " + forStudyPlanCode + " doesn't belong to any competency");
        }

        return SubjectForStudyPlan.builder()
                .studyPlanCode(forStudyPlanCode)
                .subject(subjectModelAssembler.toModel(subject))
                .timingStage(studyPlanSubject.getTimingStage())
                .timingTerm(studyPlanSubject.getTimingTerm())
                .competencies(competencyForQualifications)
                .build();
    }

    /**
     * Retrieves all subjects for a particular study plan with related data.
     *
     * @param forStudyPlanCode study plan code. Not null
     * @return list of subjects for a particular study plan
     * @throws StudyPlanNotFoundException throws if no study plan with provided id
     */
    @Override
    public List<SubjectForStudyPlan> getAllSubjects(@NonNull String forStudyPlanCode) throws StudyPlanNotFoundException {
        return studyPlanSubjectRepository.findAllByStudyPlanCodeOrderByTimingStageAscTimingTermAsc(forStudyPlanCode)
                .stream().map(ss -> getOneSubject(forStudyPlanCode, ss.getStudyplanSubjectPK().getSubjectCode()))
                .collect(Collectors.toList());
    }
}
