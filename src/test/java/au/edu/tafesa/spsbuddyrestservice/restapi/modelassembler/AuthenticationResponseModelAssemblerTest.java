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
package au.edu.tafesa.spsbuddyrestservice.restapi.modelassembler;

import au.edu.tafesa.spsbuddyrestservice.model.AuthenticationRequestBody;
import au.edu.tafesa.spsbuddyrestservice.restapi.controller.AuthenticationController;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Tests for AuthenticationResponseModelAssembler.
 * 
 * @author Fedor Gabrus
 */
@Slf4j
public class AuthenticationResponseModelAssemblerTest {

    /**
     * Test of toModel method, of class AuthenticationResponseModelAssembler.
     */
    @Test
    public void testToModel() {
        try {
            log.debug(linkTo(AuthenticationController.class.getMethod("authenticate", AuthenticationRequestBody.class),
                    new AuthenticationRequestBody()).toString());
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(AuthenticationResponseModelAssemblerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(AuthenticationResponseModelAssemblerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
