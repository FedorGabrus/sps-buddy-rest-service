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

import au.edu.tafesa.spsbuddyrestservice.exception.AuthorizationTokenExpiredException;
import au.edu.tafesa.spsbuddyrestservice.model.JwsPayloadImpl;
import io.jsonwebtoken.JwtException;
import java.time.ZonedDateTime;

/**
 * Jws is an interface describing java class that generates and processes signed JSON web tokens.
 * 
 * @author Fedor Gabrus
 */
public interface Jws {
    
    /**
     * Generates a new token encoded in BASE64.
     * 
     * @param audience recipients that the JWT is intended for
     * @param jwtId a unique identifier for the JWT
     * @param issuedAt date and time when token was issued
     * 
     * @return BASE64 encoded token
     */
    String createEncodedJws(String audience, String jwtId, ZonedDateTime issuedAt);
    
    /**
     * Verifies signature and extracts body payload.
     * 
     * @param token BASE64 encoded token
     * @return token payload
     * @throws JwtException throws when signature verification fails or token has improper format
     * @throws AuthorizationTokenExpiredException throws when token expired
     */
    JwsPayloadImpl verifySignatureAndGetPayload(String token) throws JwtException, AuthorizationTokenExpiredException;
    
}
