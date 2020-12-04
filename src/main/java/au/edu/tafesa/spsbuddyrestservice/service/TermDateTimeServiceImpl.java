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

import au.edu.tafesa.spsbuddyrestservice.component.modelassembler.TermDateTimeModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.TermDatetimePK;
import au.edu.tafesa.spsbuddyrestservice.model.TermDateTimeDTO;
import au.edu.tafesa.spsbuddyrestservice.repository.business.TermDateTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Handles term date and time related data and operations.
 *
 * @author Fedor Gabrus
 */
@Service
public class TermDateTimeServiceImpl implements TermDateTimeService {

    private final TermDateTimeRepository termDateTimeRepository;
    private final TermDateTimeModelAssembler termDateTimeModelAssembler;

    @Autowired
    public TermDateTimeServiceImpl(TermDateTimeRepository termDateTimeRepository,
                                   TermDateTimeModelAssembler termDateTimeModelAssembler) {
        this.termDateTimeRepository = termDateTimeRepository;
        this.termDateTimeModelAssembler = termDateTimeModelAssembler;
    }

    /**
     * Retrieves particular term dates.
     *
     * @param termCode term number
     * @param termYear year
     * @return term date time representation model wrapped in optional
     */
    @Override
    public Optional<TermDateTimeDTO> getOneTermDateTime(int termCode, int termYear) {
        return termDateTimeRepository.findById(new TermDatetimePK(termCode, termYear))
                .map(termDateTimeModelAssembler::toModel);
    }
}
