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

import au.edu.tafesa.spsbuddyrestservice.model.AuthenticationResponseDTO;
import au.edu.tafesa.spsbuddyrestservice.model.EmailPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    
    // Text for bad credentials.
    private static final String BAD_CREDENTIALS = "Bad credentials";
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/login")
    public EntityModel<AuthenticationResponseDTO> authenticateWebApp(
            @RequestBody EmailPasswordDTO emailPasswordDTO) throws BadCredentialsException {
        
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailPasswordDTO.getEmail(), emailPasswordDTO.getPassword()));
        }
        catch (AuthenticationException e) {
            // Throws exception if authentication failes.
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }
        // TODO
        return null;
    }
    
}
