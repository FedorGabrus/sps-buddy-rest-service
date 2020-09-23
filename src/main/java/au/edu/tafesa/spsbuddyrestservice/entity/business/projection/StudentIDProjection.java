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
 * Projection to retrieve only student's ID from student table.
 * 
 * @author Fedor Gabrus
 */
public interface StudentIDProjection {
    
    /**
     * ID getter from Student entity.
     * 
     * @return student's ID
     */
    String getStudentID();
    
    /**
     * ID setter from Student entity.
     * Used mostly in tests.
     * 
     * @param studentID new student's ID
     */
    void setStudentID(String studentID);
    
}
