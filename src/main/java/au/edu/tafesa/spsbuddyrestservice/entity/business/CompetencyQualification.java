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

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.CompetencyQualificationPK;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents competency_qualification table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "competency_qualification")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CompetencyQualification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private CompetencyQualificationPK competencyQualificationPK;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("qualCode")
    @JoinColumn(name = "QualCode")
    @ToString.Exclude
    private Qualification qualification;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("nationalCompCode")
    @JoinColumn(name = "NationalCompCode", referencedColumnName = "NationalCompCode")
    @ToString.Exclude
    private Competency competency;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @JoinColumn(name = "CompTypeCode")
    @ToString.Exclude
    private CompetencyType competencyType;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.competencyQualificationPK);
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
        final CompetencyQualification other = (CompetencyQualification) obj;
        return Objects.equals(this.competencyQualificationPK, other.competencyQualificationPK);
    }
    
}
