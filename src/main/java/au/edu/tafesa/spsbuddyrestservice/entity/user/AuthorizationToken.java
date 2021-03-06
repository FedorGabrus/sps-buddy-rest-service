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

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Represents authorizationtoken table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "authorizationtoken")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthorizationToken implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "UserEmail")
    private String userEmail;
    
    @Basic(optional = false)
    @Column(name = "TokenUID")
    private String tokenUID;
    
    @Basic(optional = false)
    @Column(name = "IssuedAt")
    private ZonedDateTime issueDateTime;
    
    /**
     * Constructor without OneToOne relation.
     * 
     * @param userEmail user's email
     * @param uid token UID
     * @param issuedAt timestamp
     */
    public AuthorizationToken(@NonNull String userEmail, @NonNull String uid, @NonNull ZonedDateTime issuedAt) {
        this.userEmail = userEmail;
        tokenUID = uid;
        issueDateTime = issuedAt;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.userEmail);
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
        final AuthorizationToken other = (AuthorizationToken) obj;
        return Objects.equals(this.userEmail, other.userEmail);
    }
    
}
