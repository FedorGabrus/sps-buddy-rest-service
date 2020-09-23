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

import au.edu.tafesa.spsbuddyrestservice.config.JwsConfig;
import au.edu.tafesa.spsbuddyrestservice.exception.AuthorizationTokenExpiredException;
import au.edu.tafesa.spsbuddyrestservice.model.JwsPayloadImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.time.ZonedDateTime;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * JwsUtility test.
 * 
 * @author Fedor Gabrus
 */
public class JwsUtilityTest {
    
    private final long TOKEN_LIFE_SPAN = 10;
    private final String SIGNATURE = "Zkq9LSXFOpxeh7_3Ev94ag_9Gqls5WsFCrMAgMPuKmw";

    private final JwsUtility instance;
    
    /**
     * Constructor.
     * Populates required fields. 
     */
    public JwsUtilityTest() {
        // Sets token lifespan.
        instance = new JwsUtility();
        ReflectionTestUtils.setField(instance, "tokenLifeSpan", TOKEN_LIFE_SPAN);
        
        // Sets secret key.
        var jwsConfig = new JwsConfig();
        ReflectionTestUtils.setField(jwsConfig, "signatureKey", SIGNATURE);
        ReflectionTestUtils.setField(instance, "jwsSecretKey", jwsConfig.jwsSecretKeyBean());
    }

    /**
     * Test of createEncodedJws method, of class JwsUtility.
     */
    @Test
    public void testCreateEncodedJws() {
        assertThat(instance.createEncodedJws("email", "UID", ZonedDateTime.now())).as("Create token")
                .isNotBlank();
    }

    /**
     * Test of verifySignatureAndGetPayload method, of class JwsUtility.
     */
    @Test
    public void testVerifySignatureAndGetPayload() {
        // Token payload.
        final String email = "email";
        final String uid = "UID";
        final var issuedAt = ZonedDateTime.now();
        
        final String validToken = instance.createEncodedJws(email, uid, issuedAt);
        
        // Test that no exception with a valid token.
        assertThatCode(() -> instance.verifySignatureAndGetPayload(validToken))
                .as("No exception with valid token")
                .doesNotThrowAnyException();
        
        // Test that proper exception thrown when token expiered.
        assertThatExceptionOfType(AuthorizationTokenExpiredException.class)
                .as("Expired token exception")
                .isThrownBy(() -> instance.verifySignatureAndGetPayload(
                        // Creates expired token.
                        instance.createEncodedJws(email, uid, issuedAt.minusMinutes(TOKEN_LIFE_SPAN + 1))));
        
        // Test that token contains expected payload.
        assertThat(instance.verifySignatureAndGetPayload(validToken))
                .as("Verify token payload")
                // Token payload contains timestamp with less presigion than ZonedDateTime.now().
                .isEqualToComparingFieldByField(new JwsPayloadImpl(email, uid, issuedAt));
        
        // Test token with wrong signature.
        var instanceWithNewSecretKey = new JwsUtility();
        ReflectionTestUtils.setField(instanceWithNewSecretKey, "tokenLifeSpan", TOKEN_LIFE_SPAN);
        // Config file for new secret key.
        var jwsConfig = new JwsConfig();
        ReflectionTestUtils.setField(jwsConfig, "signatureKey", "R-7vfZFj-bOStrrlyGmvDl2s9zdFvhG5G1iq0GPVCiE");
        ReflectionTestUtils.setField(instanceWithNewSecretKey, "jwsSecretKey", jwsConfig.jwsSecretKeyBean());
        
        assertThatExceptionOfType(JwtException.class)
                .as("Invalid signature")
                .isThrownBy(() -> instance.verifySignatureAndGetPayload(
                        instanceWithNewSecretKey.createEncodedJws(email, uid, issuedAt))
                );
        
        // Test token with missing fieldes.
        assertThatExceptionOfType(JwtException.class)
                .as("Token misses requiered fields exception")
                .isThrownBy(() -> instanceWithNewSecretKey.verifySignatureAndGetPayload(
                        Jwts.builder().setAudience("Test").signWith(jwsConfig.jwsSecretKeyBean()).compact()
                ))
                .withMessage("Provided token has valid signature but missing required claims");
    }
    
}
