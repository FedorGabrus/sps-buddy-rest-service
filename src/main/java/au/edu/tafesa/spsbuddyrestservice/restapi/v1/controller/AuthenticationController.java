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
package au.edu.tafesa.spsbuddyrestservice.restapi.v1.controller;

import au.edu.tafesa.spsbuddyrestservice.model.AuthenticationResponse;
import au.edu.tafesa.spsbuddyrestservice.model.EmailPasswordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication end points.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthenticationController {
    
    // Text for bad credentials.
    private static final String BAD_CREDENTIALS = "Bad credentials";
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/authenticate")
    public EntityModel<AuthenticationResponse> authenticateWebApp(
            @RequestBody EmailPasswordDTO emailPasswordDTO) throws BadCredentialsException {
        try {
            final Authentication authorisedUser = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailPasswordDTO.getEmail(), emailPasswordDTO.getPassword()));
            log.debug("User authenticated.");
        }
        catch (AuthenticationException e) {
            // Throws exception if authentication failes.
            log.debug(BAD_CREDENTIALS);
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }
        
        // TODO
        return null;
    }
    
}
