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

package au.edu.tafesa.spsbuddyrestservice.repository.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.StudyPlanQualification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudyPlanQualificationRepository extends CrudRepository<StudyPlanQualification, String> {

    /**
     * Retrieves list of {@link StudyPlanQualification} entities that belong to a particular
     * {@link au.edu.tafesa.spsbuddyrestservice.entity.business.Qualification}. Sorted by priority.
     *
     * @param qualificationCode qualification code
     * @return list of StudyPlanQualification entities
     */
    List<StudyPlanQualification> findAllByQualificationCodeOrderByPriorityAsc(String qualificationCode);

}
