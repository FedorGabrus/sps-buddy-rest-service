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

import au.edu.tafesa.spsbuddyrestservice.model.User;
import au.edu.tafesa.spsbuddyrestservice.model.UserAuthority;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Provides helper methods to manage end-points access.
 * Should be used only in controllers.
 */
@Slf4j
@Component
public class EndPointAccessImpl implements EndPointAccess {

    @Override
    public void throw403IfUserIsStudentAndHasAnotherId(@NonNull String studentId) throws AccessDeniedException {
        // Retrieves logged in user.
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Throws runtime exception if user is not logged in.
        if (user == null) {
            final String error = "User is not logged in";
            log.error("error");
            throw new RuntimeException(error);
        }
        // Throws AccessDeniedException if user is a student but has another id.
        if (user.hasThisAuthority(UserAuthority.ROLE_STUDENT) && !user.hasThisId(studentId)) {
            log.debug("Student tried to access info for another id");
            throw new AccessDeniedException("Access Denied");
        }
    }

}
