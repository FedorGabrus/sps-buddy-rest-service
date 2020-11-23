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

import au.edu.tafesa.spsbuddyrestservice.exception.LecturerNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.LecturerDTO;
import au.edu.tafesa.spsbuddyrestservice.service.LecturerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Lecturer related end-points.
 *
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    /**
     * Retrieves lecturer with a particular ID.
     * Returns 404 if lecturer isn't found
     *
     * @param id lecturer's id
     * @return lecturer representation model
     */
    @GetMapping("/{id}")
    public LecturerDTO getOneLecturer(@PathVariable String id) {
        return lecturerService.getOneLecturer(id).orElseThrow(LecturerNotFoundException::new);
    }

}
