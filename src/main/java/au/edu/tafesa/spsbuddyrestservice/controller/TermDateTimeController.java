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

import au.edu.tafesa.spsbuddyrestservice.exception.TermDateTimeNotFoundException;
import au.edu.tafesa.spsbuddyrestservice.model.TermDateTimeDTO;
import au.edu.tafesa.spsbuddyrestservice.service.TermDateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Term date time related end-points.
 *
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/term-date-times")
public class TermDateTimeController {

    private final TermDateTimeService termDateTimeService;

    @Autowired
    public TermDateTimeController(TermDateTimeService termDateTimeService) {
        this.termDateTimeService = termDateTimeService;
    }

    /**
     * Retrieves one particular term date and time.
     * Returns 404 if term not found.
     *
     * @param termYear term year
     * @param termCode term code
     * @return term date and time representation model
     */
    @GetMapping("/{termYear}/{termCode}")
    public TermDateTimeDTO getOneTermDateTime(@PathVariable Integer termYear, @PathVariable Integer termCode) {
        return termDateTimeService.getOneTermDateTime(termCode, termYear).orElseThrow(TermDateTimeNotFoundException::new);
    }

}
