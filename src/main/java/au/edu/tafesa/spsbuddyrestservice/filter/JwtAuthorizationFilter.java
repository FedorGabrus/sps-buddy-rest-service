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
package au.edu.tafesa.spsbuddyrestservice.filter;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Security authorization filter. Authorizes user if request contains a valid JWS.
 * 
 * @author Fedor Gabrus
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    
    // Header for authorization token.
    private static final String AUTHORIZATION_HEADER =  "Authorization";
    // Authorization token prefix.
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        extractToken(request).ifPresent(token -> {
            // TODO
        });
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
                // Checks if header starts with the proper prefix.
                .filter(header -> header.startsWith(AUTHORIZATION_HEADER_PREFIX))
                // Removes prefix.
                .map(header -> header.substring(AUTHORIZATION_HEADER_PREFIX.length()));
    }
    
}
