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
package au.edu.tafesa.spsbuddyrestservice.component;

import au.edu.tafesa.spsbuddyrestservice.exception.InconsistentDataException;
import au.edu.tafesa.spsbuddyrestservice.model.ResponseErrorBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Handles JwtException and InconsistentDataException.
 * 
 * @author Fedor Gabrus
 */
@Slf4j
@Component
public class JwtAuthorizationExceptionFilter extends GenericFilterBean {

    @Autowired
    private ObjectMapper errorObjectMapper;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        }
        catch (JwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized", "Bad token");
        }
        catch (InconsistentDataException e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error",
                    "Inconsistent user's data");
        }
    }
    
    /**
     * Sets 401 to the response and populates it with appropriate data.
     * 
     * @param response
     * @param errorMessage message that will be included in response body
     * @throws IOException 
     */
    private void sendErrorResponse(ServletResponse response, int errorCode, String error, String errorMessage)
            throws IOException {
        
        final var errorResponse = new HttpServletResponseWrapper((HttpServletResponse) response);
        errorResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        errorResponse.setStatus(errorCode);
        
        // Populate response body.
        try {
            errorResponse.getWriter().write(errorObjectMapper.writeValueAsString(
                    new ResponseErrorBody(LocalDateTime.now().toString(), errorCode, error, errorMessage)));
        } catch (IOException ex) {
            log.error("Failed to serialize ResponseErrorBody to JSON");
        }
        
        errorResponse.flushBuffer();
    }
    
}
