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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AppDateTime provides proper date and time depending on application
 time zone.
 * 
 * @author Fedor Gabrus
 */
@Component
public class AppDateTime implements DateTime {

    @Autowired
    private ZoneId appTimeZone;
    
    /**
     * Gets current date and time depending on application properties.
     * 
     * @return zoned date and time
     */
    @Override
    public ZonedDateTime now() {
        return ZonedDateTime.now(appTimeZone);
    }

    @Override
    public ZonedDateTime ofInstant(@NonNull Instant instant) {
        return ZonedDateTime.ofInstant(instant, appTimeZone);
    }
    
}
