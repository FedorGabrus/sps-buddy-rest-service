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
import au.edu.tafesa.spsbuddyrestservice.model.AuthenticationResponseBody;
import au.edu.tafesa.spsbuddyrestservice.restapi.controller.AuthenticationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * Converts AuthenticationResponseBody into EntityModel.
 * 
 * @author Fedor Gabrus
 */
@Slf4j
@Component
public class AuthenticationResponseModelAssembler
        implements RepresentationModelAssembler<AuthenticationResponseBody, EntityModel<AuthenticationResponseBody>> {

    /**
     * Converts AuthenticationResponseBody into EntityModel.
     * 
     * @param responseBody AuthenticationResponseBody to convert
     * @return EntityModel
     */
    @Override
    public EntityModel<AuthenticationResponseBody> toModel(AuthenticationResponseBody responseBody) {
        final var model = EntityModel.of(responseBody);
        
        try {
            // Adds link to self.
            model.add(linkTo(AuthenticationController.class.getMethod("logIn", AuthenticationRequestBody.class),
                    (AuthenticationRequestBody) null).withSelfRel());
            
            // Adds link to log out.
            model.add(linkTo(AuthenticationController.class.getMethod("logOut", Authentication.class),
                    (UsernamePasswordAuthenticationToken) null).withRel("log out"));
        } catch (NoSuchMethodException | SecurityException ex) {
            final String error = "Link creation failed";
            log.error(error);
            throw new RuntimeException(error);
        }
                
        return model;
    }

    
    
}
