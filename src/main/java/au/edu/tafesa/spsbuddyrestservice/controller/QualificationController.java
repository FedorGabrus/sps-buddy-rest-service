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

package au.edu.tafesa.spsbuddyrestservice.controller;

import au.edu.tafesa.spsbuddyrestservice.exception.CompetencyNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.QualificationNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.exception.SubjectNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.CompetencyForQualification;
import au.edu.tafesa.spsbuddyrestservice.model.QualificationDTO;
import au.edu.tafesa.spsbuddyrestservice.model.SubjectForQualification;
import au.edu.tafesa.spsbuddyrestservice.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Qualification related end-points.
 *
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/qualifications")
public class QualificationController {

    private final QualificationService qualificationService;

    @Autowired
    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    /**
     * Retrieves qualification with a particular id.
     * <p>Returns 404 if qualification not found.</p>
     *
     * @param qualificationCode qualification code
     * @return qualification representation model
     */
    @GetMapping("/{qualificationCode}")
    public QualificationDTO getOneQualification(@PathVariable String qualificationCode) {
        return qualificationService.getOneQualification(qualificationCode)
                .orElseThrow(QualificationNotFoundException::new);
    }

    /**
     * Retrieves all qualifications.
     *
     * @return Collection model
     */
    @GetMapping
    public CollectionModel<QualificationDTO> getAllQualifications() {
        return CollectionModel.of(qualificationService.getAllQualifications(),
                linkTo(methodOn(QualificationController.class).getAllQualifications()).withSelfRel());
    }

    /**
     * Retrieves all competencies for a particular qualification.
     * <p>Returns 404 if qualification has no competencies.</p>
     *
     * @param forQualificationCode qualification's code
     * @return list of all competencies for a particular qualification
     */
    @GetMapping("/{forQualificationCode}/competencies")
    public CollectionModel<CompetencyForQualification> getAllCompetencies(@PathVariable String forQualificationCode) {
        final var competencies = qualificationService.getAllCompetencies(forQualificationCode);
        if (competencies.isEmpty()) {
            throw new CompetencyNotFoundException();
        }

        return CollectionModel.of(
                competencies,
                linkTo(methodOn(QualificationController.class).getAllCompetencies(forQualificationCode)).withSelfRel()
        );
    }

    /**
     * Retrieves a particular competency with related to the qualification data.
     * <p>Returns 404 if no proper competency found.</p>
     *
     * @param forQualificationCode qualification code
     * @param competencyTafeCode TAFE competency code
     * @return competency with related to the qualification data
     */
    @GetMapping("/{forQualificationCode}/competencies/{competencyTafeCode}")
    public CompetencyForQualification getOneCompetency(@PathVariable String forQualificationCode,
                                                       @PathVariable String competencyTafeCode) {
        return qualificationService.getOneCompetency(forQualificationCode, competencyTafeCode);
    }

    /**
     * Retrieves subjects for a particular qualification with related data.
     * <p>Returns 404 if qualification not found or it doesn't have any subjects.</p>
     *
     * @param forQualificationCode qualification code
     * @return list of subjects for a particular qualification with related data
     */
    @GetMapping("/{forQualificationCode}/subjects")
    public CollectionModel<SubjectForQualification> getAllSubjects(@PathVariable String forQualificationCode) {
        final var subjects = qualificationService.getAllSubjects(forQualificationCode);
        if (subjects.isEmpty()) {
            throw new SubjectNotFoundException();
        }

        return CollectionModel.of(
                subjects,
                linkTo(methodOn(QualificationController.class).getAllSubjects(forQualificationCode)).withSelfRel()
        );
    }

    /**
     * Retrieves a particular subject for the qualification.
     * <p>Returns 404 if no related to qualification subject found</p>
     *
     * @param forQualificationCode qualification code
     * @param subjectCode subject code
     * @return subject with related to the qualification data
     */
    @GetMapping("/{forQualificationCode}/subjects/{subjectCode}")
    public SubjectForQualification getOneSubject(@PathVariable String forQualificationCode,
                                                 @PathVariable String subjectCode) {
        return qualificationService.getOneSubject(forQualificationCode, subjectCode);
    }

}
