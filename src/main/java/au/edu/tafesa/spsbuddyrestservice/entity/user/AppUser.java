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
package au.edu.tafesa.spsbuddyrestservice.entity.user;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents appuser table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "appuser", schema = "sps_buddy_users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppUser implements Serializable, User {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    
    @Basic(optional = false)
    @Column(name = "Password")
    @ToString.Exclude
    private String password;
    
    @Basic(optional = false)
    @Column(name = "Enabled")
    private boolean enabled;
    
    @ManyToOne(
            optional = false,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "RoleID")
    private UserRole role;
    
    @OneToOne(
            optional = true,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @MapsId
    private AuthorizationToken token;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.email);
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
        final AppUser other = (AppUser) obj;
        return Objects.equals(this.email, other.email);
    }

    @Override
    public String getUserRoleName() {
        return role.getRoleName();
    }
    
}
