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
package au.edu.tafesa.spsbuddyrestservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Throws when user and business DBs have inconsistent data.
 * 
 * @author Fedor Gabrus
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Inconsistent data")
public class InconsistentDataException extends RuntimeException {
    
    public InconsistentDataException(String message) {
        super(message);
    }
    
}
