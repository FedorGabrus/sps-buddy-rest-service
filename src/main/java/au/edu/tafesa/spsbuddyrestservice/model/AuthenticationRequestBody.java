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
package au.edu.tafesa.spsbuddyrestservice.model;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model to capture user's email and password.
 * 
 * @author Fedor Gabrus
 */
@Data
@NoArgsConstructor
public class AuthenticationRequestBody implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @NotBlank(message = "email should not be empty")
    private String email;
    @NotBlank(message = "password should not be empty")
    private String password;
    
}
