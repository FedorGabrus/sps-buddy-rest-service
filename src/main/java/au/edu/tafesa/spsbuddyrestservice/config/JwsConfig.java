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

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that provides beans for authorization tokens.
 * 
 * @author Fedor Gabrus
 */
@Configuration
public class JwsConfig {
    
    @Value("${app.jwt.secret}")
    private String signatureKey;
    
    /**
     * Creates a secret key from provided signature key. Used to sign JWS tokens.
     *
     * @return secret key required for token signature
     */
    @Bean
    public SecretKey jwsSecretKeyBean() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signatureKey));
    }
    
}
