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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import org.junit.jupiter.api.Test;

/**
 * Unit test for JwsPayloadImpl class.
 * 
 * @author Fedor Gabrus
 */
public class JwsPayloadImplTest {
    
    public JwsPayloadImplTest() {
    }
    
    /**
     * Test of constructor.
     */
    @Test
    void testConstructor() {
        // Test that constructor throws Null pointer exception.
        assertThatCode(() -> new JwsPayloadImpl("email", "UID", ZonedDateTime.now())).doesNotThrowAnyException();
        assertThat(new JwsPayloadImpl("email", "UID", ZonedDateTime.now())).isNotNull();
        assertThatNullPointerException().isThrownBy(() -> new JwsPayloadImpl(null, null, null));
        assertThatNullPointerException().isThrownBy(() -> new JwsPayloadImpl(null, "UID", ZonedDateTime.now()));
        assertThatNullPointerException().isThrownBy(() -> new JwsPayloadImpl("email", null, ZonedDateTime.now()));
        assertThatNullPointerException().isThrownBy(() -> new JwsPayloadImpl("email", "UID", null));
    }

    /**
     * Test of isAlmostEqualToIssueDate method, of class JwsPayloadImpl.
     */
    @Test
    void testIsAlmostEqualToIssueDate() {
        final var timeDate = ZonedDateTime.now();
        
        // Tests that converted from Date ZonedDatetime compared properly.
        assertThat(new JwsPayloadImpl(
                "email",
                "UID",
                ZonedDateTime.ofInstant(Date.from(timeDate.toInstant()).toInstant(), ZoneId.systemDefault()))
                .isAlmostEqualToIssueDate(timeDate))
                .as("Almost Equal")
                .isTrue();
        
        // Test that acceptable imprecision is not more than 1 second.
        assertThat(new JwsPayloadImpl("email", "UID", timeDate).isAlmostEqualToIssueDate(timeDate.plusSeconds(1)))
                .as("Not equal +1 second")
                .isFalse();
        assertThat(new JwsPayloadImpl("email", "UID", timeDate).isAlmostEqualToIssueDate(timeDate.minusSeconds(1)))
                .as("Not equal -1 second")
                .isFalse();
    }
    
    /**
     * Test of getUserEmail method.
     */
    @Test
    void testGetUserEmail() {
        final String email = "email";
        assertThat(new JwsPayloadImpl(email, "UID", ZonedDateTime.now()).getUserEmail())
                .as("Test email getter")
                .isEqualTo(email);
    }
 
    /**
     * Test of getTokenUid method.
     */
    @Test
    void testGetTokenUid() {
        final String uid = "uid";
        assertThat(new JwsPayloadImpl("email", uid, ZonedDateTime.now()).getTokenUid())
                .as("Test uid getter")
                .isEqualTo(uid);
    }
    
}
