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

import javax.crypto.SecretKey;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * JwsConfig Unit test
 * 
 * @author Fedor Gabrus
 */
public class JwsConfigTest {

    /**
     * Test of jwsSecretKeyBean method, of class JwsConfig.
     */
    @Test
    public void testJwsSecretKeyBean() {
        var instance = new JwsConfig();
        ReflectionTestUtils.setField(instance, "signatureKey", "Zkq9LSXFOpxeh7_3Ev94ag_9Gqls5WsFCrMAgMPuKmw");
        
        assertThat(instance.jwsSecretKeyBean()).as("Secret key created")
                .isNotNull()
                .isInstanceOf(SecretKey.class);
    }
    
}
