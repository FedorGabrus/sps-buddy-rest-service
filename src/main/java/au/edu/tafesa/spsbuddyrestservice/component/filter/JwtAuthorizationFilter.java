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
package au.edu.tafesa.spsbuddyrestservice.component.filter;

import au.edu.tafesa.spsbuddyrestservice.component.JwsUtility;
import au.edu.tafesa.spsbuddyrestservice.controller.AuthenticationController;
import au.edu.tafesa.spsbuddyrestservice.exception.InconsistentDataException;
import au.edu.tafesa.spsbuddyrestservice.model.JwsPayload;
import au.edu.tafesa.spsbuddyrestservice.model.User;
import au.edu.tafesa.spsbuddyrestservice.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Security authorization filter. Authorizes user if request contains a valid JWS.
 * Throws an error if token is not valid or expired.
 * Intercepts each request.
 * Skips filtering if destination is the authentication end-point.
 * 
 * @author Fedor Gabrus
 */
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    /**
     * Authorization token header.
     */
    private static final String AUTHORIZATION_HEADER =  "Authorization";

    /**
     * Authorization token prefix.
     */
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
    
    private final JwsUtility jwsUtility;
    private final UserService appUserService;

    /**
     * Stores path to the authentication end-point.
     */
    private final String pathToAuthenticationEndPoint;

    /**
     * Constructor.
     */
    public JwtAuthorizationFilter(JwsUtility jwsUtility, UserService appUserService) {
        pathToAuthenticationEndPoint = getPathToAuthenticationEndPoint();
        this.jwsUtility = jwsUtility;
        this.appUserService = appUserService;
    }
    
    /**
     * Extracts and processes JWT token from request header.
     * Tries to authenticate user based on the token data.
     * 
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException 
     * @throws JwtException throws if authorization filter is not valid
     * @throws InconsistentDataException throws when user found in user DB, but not in business.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, JwtException, InconsistentDataException {
        // Skips filtering if destination URL is authentication.
        if (request.getRequestURI().equals(pathToAuthenticationEndPoint)) {
            log.debug("Skips token examination");
            chain.doFilter(request, response);
            return;
        }
        
        extractToken(request).ifPresent(token -> {
            // Throws JwtException if token is not valid.
            final JwsPayload jwsPayload = jwsUtility.verifySignatureAndGetPayload(token);
            
            try {
                // Gets user from the DB. Throws UsernameNotFoundException or InconsistentDataException.
                final User user = (User) appUserService.loadUserByUsername(jwsPayload.getUserEmail());
                
                // Validate token UID and date.
                user.getAuthToken()
                        .filter(userToken -> jwsPayload.getTokenUid().equals(userToken.getUid()))
                        .filter(userToken -> jwsPayload.isAlmostEqualToIssueDate(userToken.getIssuedAt()))
                        .orElseThrow(() -> {
                            final String error = "Old or invalid token";
                            log.debug(error);
                            return new JwtException(error);
                        });
                
                // Tries to authenticate user.
                if (user.isAccountNonExpired() && user.isAccountNonLocked() && user.isCredentialsNonExpired()
                        && user.isEnabled()) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(user, null,
                                    user.getAuthorities()));
                    log.debug("User " + user.getUsername() + " authenticated with JWT token");
                }
            }
            catch (UsernameNotFoundException e) {
                final String error = "User not found";
                log.debug(error);
                throw new JwtException(error);
            }
        });
        
        chain.doFilter(request, response);
    }
    
    /**
     * Retrieves authorization token from the request header.
     * 
     * @param request request to process
     * @return extracted token
     */
    private Optional<String> extractToken(HttpServletRequest request) {
        // Gets authorization header from request.
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                // Checks i f header starts with the proper prefix.
                .filter(header -> header.startsWith(AUTHORIZATION_HEADER_PREFIX))
                // Removes prefix.
                .map(header -> header.substring(AUTHORIZATION_HEADER_PREFIX.length()));
    }
    
    /**
     * Obtains path to the authentication end point.
     * 
     * @return path to the authentication end point
     */
    private static String getPathToAuthenticationEndPoint() {
        return linkTo(methodOn(AuthenticationController.class).logIn(null)).toUri().getPath();
    }
    
}
