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
package au.edu.tafesa.spsbuddyrestservice.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * JwsPayload describes class that represents jjwt token payload.
 * 
 * @author Fedor Gabrus
 */
public interface JwsPayload extends Serializable {
    
    /**
     * Allows to compare dates after conversion from legacy Date class.
     * Acceptable imprecision is less than 1 second.
     * 
     * @param dateTime date time to check equality with
     * @return true if date and time almost equal, false otherwise
     */
    boolean isAlmostEqualToIssueDate(ZonedDateTime dateTime);
    
    String getUserEmail();
    
    String getTokenUid();
    
}
