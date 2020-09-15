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
package au.edu.tafesa.spsbuddyrestservice.component;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * DateTime describes class that provides proper date and time depending on application
 * time zone.
 * 
 * @author Fedor Gabrus Gabrus
 */
public interface DateTime {
    
    /**
     * Obtains date and time for the application time zone.
     * 
     * @return ZonedDateTime for time zone specified in application properties.
     */
    ZonedDateTime now();
    
    /**
     * Obtains application zoned date time of Instant.
     * 
     * @param instant the instant to create date time from
     * @return application zoned date time
     */
    ZonedDateTime ofInstant(Instant instant);
    
}
