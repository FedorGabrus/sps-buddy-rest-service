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
package au.edu.tafesa.spsbuddyrestservice.restapi.controller;

import au.edu.tafesa.spsbuddyrestservice.exception.BadCredentialsException;
import au.edu.tafesa.spsbuddyrestservice.model.AuthenticationResponseBody;
import au.edu.tafesa.spsbuddyrestservice.model.AuthenticationRequestBody;
import au.edu.tafesa.spsbuddyrestservice.model.GenericResponseBody;
import au.edu.tafesa.spsbuddyrestservice.model.User;
import au.edu.tafesa.spsbuddyrestservice.restapi.modelassembler.AuthenticationResponseModelAssembler;
import au.edu.tafesa.spsbuddyrestservice.service.UserService;
import java.time.ZonedDateTime;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager authenticationManagerBean;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationResponseModelAssembler assembler;
    
    /**
     * Authentication end-point.
     * 
     * @param requestBody AuthenticationRequestBody object
     * @return EntityModel with AuthenticationResponseBody
     */
    @PostMapping("/login")
    public EntityModel<AuthenticationResponseBody> logIn(
            @Valid @RequestBody AuthenticationRequestBody requestBody) {
        try {
            // Authenticate user with resieved credentials. Throws AuthenticationException in case of bad credetials.
            final Authentication authorisedUser = authenticationManagerBean.authenticate(
                new UsernamePasswordAuthenticationToken(requestBody.getEmail(), requestBody.getPassword()));
            log.debug("User authenticated via controller.");
            log.debug(authorisedUser.toString());
            
            // Retrieves user details.
            final User user = (User) authorisedUser.getPrincipal();
            
            // Creates authenticationResponse;
            final var responseBody = new AuthenticationResponseBody(userService.createNewAuthorizationToken(user),
                    user.getRoleName(), user.getUserId());
            
            return assembler.toModel(responseBody);
        }
        catch (AuthenticationException e) {
            log.debug("Bad credetials");
            throw new BadCredentialsException();
        }
    }
    
    @GetMapping("/logout")
    public ResponseEntity<GenericResponseBody> logOut(
            @CurrentSecurityContext(expression = "authentication") Authentication user) {
        log.debug(user.toString());
        userService.deleteAuthorizationToken((User) user.getPrincipal());
        return ResponseEntity.ok(new GenericResponseBody(ZonedDateTime.now().toString(), 200, "User loged out"));
    }
    
}
