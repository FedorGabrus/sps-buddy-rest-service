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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents application principal user's details.
 * 
 * @author Fedor Gabrus
 */
@ToString
public class AppUserDetails implements UserDetails {
    
    private final String username;
    @ToString.Exclude
    private final String passwordHash;
    private final boolean isEnabled;
    private final List<? extends GrantedAuthority> authorities;
    
    public AppUserDetails(@NonNull String username, @NonNull String passwordHash,
            boolean isEnabled, @NonNull List<String> authorities) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEnabled = isEnabled;
        this.authorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Getter.
     * 
     * @return user's authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Getter.
     * 
     * @return user's hashed password
     */
    @Override
    public String getPassword() {
        return passwordHash;
    }

    /**
     * Getter.
     * 
     * @return user's username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Checks if user's account is not expired.
     * 
     * @return true if user's account is enabled, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    /**
     * Checks if user account is not locked.
     * 
     * @return true if user's account is enabled, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    /**
     * Checks if user's credentials are not expired.
     * 
     * @return true if user's account is enabled, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled;
    }

    /**
     * Getter.
     * 
     * @return true if user's account is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.username);
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
