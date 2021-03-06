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
package au.edu.tafesa.spsbuddyrestservice.repository.user;

import au.edu.tafesa.spsbuddyrestservice.entity.user.AppUser;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Repository to query appuser table.
 * 
 * @author Fedor Gabrus
 */
public interface AppUserRepository extends Repository<AppUser, String> {
    
    /**
     * Looks up for a user by email.
     * Ignores case.
     * 
     * @param email email to search.
     * @return AppUser wrapped in Optional container
     */
    Optional<AppUser> findByEmailIgnoreCase(String email);
    
}
