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

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for AuthenticationResponseBody model.
 * 
 * @author Fedor Gabrus
 */
public class AuthenticationResponseBodyTest {
    
    /**
     * Test of constructor.
     */
    @Test
    void testConstrucot() {
        assertThatCode(() -> new AuthenticationResponseBody("jwt", "role", "id"))
                .as("Doesn't throw exception")
                .doesNotThrowAnyException();
        assertThatNullPointerException()
                .isThrownBy(() -> new AuthenticationResponseBody(null, "role", "id"))
                .as("JWT is null");
        assertThatNullPointerException()
                .isThrownBy(() -> new AuthenticationResponseBody("jwt", null, "id"))
                .as("Role is null");
        assertThatNullPointerException()
                .isThrownBy(() -> new AuthenticationResponseBody("jwt", "role", null))
                .as("Id is null");
    }
    
}
