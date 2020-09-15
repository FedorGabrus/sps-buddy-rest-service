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
package au.edu.tafesa.spsbuddyrestservice.config;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures beans for consistent date time manipulation.
 * 
 * @author Fedor Gabrus
 */
@Configuration
public class DateTimeConfig {
    
    @Value("${app.internal.timezone}")
    private String applicationTimeZoneId;
    
    /**
     * Creates application zone id.
     * 
     * @return application zone id
     */
    @Bean
    public ZoneId appZoneIdBean() {
        return ZoneId.of(applicationTimeZoneId);
    }
    
    /**
     * Creates application zone offset.
     * 
     * @return application zone offset
     */
    @Bean
    public ZoneOffset appZoneOffsetBean() {
        return ZonedDateTime.now(appZoneIdBean()).getOffset();
    }
    
}
