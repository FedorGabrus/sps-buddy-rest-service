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

import au.edu.tafesa.spsbuddyrestservice.model.TermDateTimeDTO;

import java.util.Optional;

/**
 * Describes class that handles term date and time related data.
 *
 * @author Fedor Gabrus
 */
public interface TermDateTimeService {

    /**
     * Retrieves particular term dates.
     *
     * @param termCode term number
     * @param termYear year
     * @return term date time representation model wrapped in optional
     */
    Optional<TermDateTimeDTO> getOneTermDateTime(int termCode, int termYear);

}
