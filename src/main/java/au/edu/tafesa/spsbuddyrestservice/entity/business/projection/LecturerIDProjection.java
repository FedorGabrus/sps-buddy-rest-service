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
package au.edu.tafesa.spsbuddyrestservice.entity.business.projection;

/**
 * Projection to retrieve only lecturer's ID from lecturer table.
 * 
 * @author Fedor Gabrus
 */
public interface LecturerIDProjection {
    
    /**
     * ID getter from Lecturer entity.
     * 
     * @return lecturer's ID
     */
    String getLecturerID();
    
    /**
     * ID setter from Lecturer entity.
     * Used mostly in tests.
     * 
     * @param lecturerID lecturer's id
     */
    void setLecturerID(String lecturerID);
    
}
