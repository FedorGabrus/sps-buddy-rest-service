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

import au.edu.tafesa.spsbuddyrestservice.model.JwsPayload;
import au.edu.tafesa.spsbuddyrestservice.model.JwsPayloadImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility service that generates and processes signed JSON web tokens that used for authorization.
 * 
 * @author Fedor Gabrus
 */
@Slf4j
@Component
public class JwsUtilityImpl implements JwsUtility {
    
    @Value("${app.jwt.body.expiration}")
    private long tokenLifeSpan;
    
    @Autowired
    @Qualifier("jwsSecretKeyBean")
    private SecretKey jwsSecretKey;

    @Override
    public String createEncodedJws(@NonNull String email, @NonNull String tokenUid, @NonNull ZonedDateTime issuedAt) {
        return Jwts.builder()
                // User's email used for identification.
                .setAudience(email)
                // UID to validate token.
                .setId(tokenUid)
                // Issue date used for later validation.
                .setIssuedAt(Date.from(issuedAt.toInstant().truncatedTo(ChronoUnit.SECONDS)))
                .signWith(jwsSecretKey)
                .compact();
    }
    
    @Override
    public JwsPayload verifySignatureAndGetPayload(@NonNull String token) throws JwtException {
        // parseClaimsJws() throws JwtException when token has invalid signature.
        final Claims claims = Jwts.parserBuilder().setSigningKey(jwsSecretKey).build().parseClaimsJws(token).getBody();
        
        try {
            // JwsPayloadImpl constructor throws NullPointerException when any provided aurgument is null.
            final var tokenPayload = new JwsPayloadImpl(
                    claims.getAudience(),
                    claims.getId(),
                    ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault()));
            
            // Validates expiration date.
            if (isTokenExpired(tokenPayload.getIssuedAt())) {
                final String error = "Authorization token expired";
                log.debug(error);
                throw new JwtException(error);
            }
            
            return tokenPayload;
        }
        catch (NullPointerException e) {
            final String error = "Provided token has valid signature but missing required claims";
            log.debug(error);
            throw new JwtException(error);
        }
    }
    
    /**
     * Validates if token has not expired.
     * 
     * @param tokenIssuedAt date and time when token was issued
     * @return true if token expired, false otherwise
     */
    private boolean isTokenExpired(@NonNull ZonedDateTime tokenIssuedAt) {
        return tokenIssuedAt.plusMinutes(tokenLifeSpan).isBefore(ZonedDateTime.now());
    }
    
}
