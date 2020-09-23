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

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.TermDatetimePK;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents term_datetime table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "term_datetime")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TermDatetime implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    public TermDatetimePK termDatetimePK;
    
    @Basic(optional = false)
    @Column(name = "StartDate")
    private LocalDate startDate;
    
    @Basic(optional = false)
    @Column(name = "EndDate")
    private LocalDate endDate;
    
    @Basic(optional = false)
    @Column(name = "SemesterCode")
    private int semesterCode;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.termDatetimePK);
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
        final TermDatetime other = (TermDatetime) obj;
        return Objects.equals(this.termDatetimePK, other.termDatetimePK);
    }
    
}
