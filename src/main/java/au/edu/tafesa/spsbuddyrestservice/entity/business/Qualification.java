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
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents qualification table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "qualification")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Qualification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "QualCode")
    private String qualCode;
    
    @Basic(optional = false)
    @Column(name = "NationalQualCode")
    private String nationalQualCode;
    
    @Basic(optional = false)
    @Column(name = "TafeQualCode")
    private String tafeQualCode;
    
    @Basic(optional = false)
    @Column(name = "QualName")
    private String qualName;
    
    @Basic(optional = false)
    @Column(name = "TotalUnits")
    private int totalUnits;
    
    @Basic(optional = false)
    @Column(name = "CoreUnits")
    private int coreUnits;
    
    @Basic(optional = false)
    @Column(name = "ElectedUnits")
    private int electedUnits;
    
    @Basic(optional = false)
    @Column(name = "ReqListedElectedUnits")
    private int reqListedElectedUnits;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.qualCode);
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
        final Qualification other = (Qualification) obj;
        return Objects.equals(this.qualCode, other.qualCode);
    }
    
}
