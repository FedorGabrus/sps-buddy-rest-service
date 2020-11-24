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

import au.edu.tafesa.spsbuddyrestservice.exception.StudyPlanNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.StudyPlanInfo;
import au.edu.tafesa.spsbuddyrestservice.service.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Study plan related end-points.
 *
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/study-plans")
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    @Autowired
    public StudyPlanController(StudyPlanService studyPlanService) {
        this.studyPlanService = studyPlanService;
    }

    /**
     * Retrieves study plan info with a particular study plan code.
     * <p>Returns 404 if study plan not found</p>
     *
     * @param studyPlanCode study plan code
     * @return study plan info with a particular study plan code
     */
    @GetMapping("/{studyPlanCode}")
    public StudyPlanInfo getOneStudyPlanInfo(@PathVariable String studyPlanCode) {
        return studyPlanService.getOneStudyPlanInfo(studyPlanCode).orElseThrow(StudyPlanNotFoundException::new);
    }

}
