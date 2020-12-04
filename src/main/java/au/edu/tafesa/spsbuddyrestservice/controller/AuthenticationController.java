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
package au.edu.tafesa.spsbuddyrestservice.controller;

import au.edu.tafesa.spsbuddyrestservice.exception.BadCredentialsException;
import au.edu.tafesa.spsbuddyrestservice.model.GenericResponseBody;
import au.edu.tafesa.spsbuddyrestservice.model.LogInForm;
import au.edu.tafesa.spsbuddyrestservice.model.LogInResponse;
import au.edu.tafesa.spsbuddyrestservice.model.User;
import au.edu.tafesa.spsbuddyrestservice.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Authentication end points.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    
    private final AuthenticationManager authenticationManagerBean;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManagerBean, UserService userService) {
        this.authenticationManagerBean = authenticationManagerBean;
        this.userService = userService;
    }

    /**
     * Authentication end-point.
     * 
     * @param requestBody LogInForm object
     * @return EntityModel with LogInResponse
     */
    @PostMapping("/login")
    public EntityModel<LogInResponse> logIn(@Valid @RequestBody LogInForm requestBody) {
        try {
            // Authenticate user with received credentials. Throws AuthenticationException in case of bad credentials.
            final Authentication authorisedUser = authenticationManagerBean.authenticate(
                new UsernamePasswordAuthenticationToken(requestBody.getEmail(), requestBody.getPassword()));
            log.debug("User authenticated via controller.");
            log.debug(authorisedUser.toString());
            
            // Retrieves user details.
            final User user = (User) authorisedUser.getPrincipal();

            // Creates entity model.
            final var model = EntityModel.of(
                    new LogInResponse(userService.createNewAuthorizationToken(user),user.getRoleName(),
                            user.getUserId()));

            // Adds link to self.
            model.add(linkTo(methodOn(AuthenticationController.class).logIn(requestBody)).withSelfRel());
            // Adds link to log out.
            model.add(linkTo(methodOn(AuthenticationController.class).logOut(user)).withRel("log out"));
            // Adds student related links.
            if (user.isStudent()) {
                // Adds link to self student.
                model.add(linkTo(methodOn(StudentController.class).getOneStudent(user.getUserId()))
                        .withRel("self student"));
            }
            // Adds lecturer related links.
            else if (user.isLecturer()) {
                // Adds link to self lecturer.
                model.add(linkTo(methodOn(LecturerController.class).getOneLecturer(user.getUserId()))
                        .withRel("self lecturer"));
            }

            return model;
        }
        catch (AuthenticationException e) {
            log.debug("Bad credentials");
            throw new BadCredentialsException();
        }
    }
    
    /**
     * Log out end point.
     * Removes token from DB for authenticated user.
     * 
     * @param user logged in user
     * @return 200 response
     */
    @GetMapping("/logout")
    public ResponseEntity<EntityModel<GenericResponseBody>> logOut(
            @NonNull @CurrentSecurityContext(expression = "authentication.principal") User user) {
        userService.deleteAuthorizationToken(user);
        log.debug("Logging out " + user.getEmail());
        
        return ResponseEntity.ok(EntityModel.of(new GenericResponseBody("User logged out"),
                linkTo(methodOn(AuthenticationController.class).logOut(user)).withSelfRel(),
                linkTo(methodOn(AuthenticationController.class).logIn(null)).withRel("login")));
    }
    
}
