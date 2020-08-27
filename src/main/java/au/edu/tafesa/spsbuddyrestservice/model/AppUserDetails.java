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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents application principal user's details.
 * Assumes that user can have only one role.
 * 
 * @author Fedor Gabrus
 */
@Getter
@ToString
public class AppUserDetails implements UserDetails {
    
    private final String username;
    @ToString.Exclude
    private final String password;
    private final boolean enabled;
    private final List<SimpleGrantedAuthority> authorities;
    
    /**
     * Constructor.
     * 
     * @param username user identifier.
     * @param password password/password hash
     * @param enabled determines if account is active
     * @param role user's role name
     */
    public AppUserDetails(@NonNull String username, @NonNull String password, boolean enabled,
            @NonNull String role) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        // Creates authorities based on provided user role.
        this.authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
    }
    
    /**
     * Determines if account expired.
     * 
     * Not implemented.
     * 
     * @return false if account expired, true otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Determines if account locked.
     * 
     * Not implemented.
     * 
     * @return false if account locked, true otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Determines if credentials expired.
     * 
     * Not implemented.
     * 
     * @return false if credentials expired, true otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.username);
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
        final AppUserDetails other = (AppUserDetails) obj;
        return Objects.equals(this.username, other.username);
    }
    
}
