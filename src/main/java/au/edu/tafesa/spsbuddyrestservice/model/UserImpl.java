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

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Represents application principal user's details.
 * Assumes that user can have only one role.
 * 
 * @author Fedor Gabrus
 */
@ToString
@Builder
@Getter
@Setter
public final class UserImpl implements User {
    
    private static final long serialVersionUID = 1L;
    
    // Either student or lecturer id.
    @NonNull
    private final String userId;
    
    @NonNull
    private final String email;
    
    @NonNull
    @ToString.Exclude
    private final String password;
    
    // Determines whether account is active.
    private final boolean enabled;
    
    @NonNull
    private final UserAuthority role;
    
    private UserToken authToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.toString()));
    }

    /**
     * Getter for user's authorization token.
     * 
     * @return user's authorization token wrapped in optional
     */
    @Override
    public Optional<UserToken> getAuthToken() {
        return Optional.ofNullable(authToken);
    }
    
    /**
     * Obtains username. Application uses emails as user identifiers.
     * 
     * @return user's email
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Not implemented.
     * 
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Not implemented.
     * 
     * @return 
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Not implemented.
     * 
     * @return 
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserImpl other = (UserImpl) obj;
        return Objects.equals(this.email, other.email);
    }

    @Override
    public String getRoleName() {
        return role.toString();
    }
    
}