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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents payload of a jjwt authorization token.
 * 
 * @author Fedor Gabrus
 */
@Getter
@EqualsAndHashCode
@ToString
public class JwsPayloadImpl implements JwsPayload {
    
    private static final long serialVersionUID = 1L;
    
    @NonNull
    private final String userEmail;
    @NonNull
    private final String tokenUid;
    @NonNull
    private final ZonedDateTime issuedAt;

    /**
     * Constructor.
     * 
     * @param userEmail user's email, not null
     * @param tokenUid token UID, not null
     * @param issuedAt issued date, not null
     */
    public JwsPayloadImpl(@NonNull String userEmail, @NonNull String tokenUid, @NonNull ZonedDateTime issuedAt) {
        this.userEmail = userEmail;
        this.tokenUid = tokenUid;
        this.issuedAt = issuedAt.truncatedTo(ChronoUnit.SECONDS);
    }
    
    /**
     * Allows to compare dates after conversion from legacy Date class.
     * Acceptable imprecision is less than 1 second.
     * 
     * @param dateTime date time to check equality with, non null
     * @return true if date and time almost equal, false otherwise
     */
    @Override
    public boolean isAlmostEqualToIssueDate(@NonNull ZonedDateTime dateTime) {
        return issuedAt.isEqual(dateTime.truncatedTo(ChronoUnit.SECONDS));
    }
    
}
