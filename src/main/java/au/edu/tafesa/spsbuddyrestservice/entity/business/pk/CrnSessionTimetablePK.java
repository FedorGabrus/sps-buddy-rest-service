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
package au.edu.tafesa.spsbuddyrestservice.entity.business.pk;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents PK for crn_session_timetable table.
 * @author Fedor
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrnSessionTimetablePK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Embedded
    private CrnDetailPK crnDetailPK;
    
    @Basic(optional = false)
    @Column(name = "DayCode")
    private int dayCode;
    
    @Basic(optional = false)
    @Column(name = "StartTime")
    private LocalTime startTime;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.crnDetailPK);
        hash = 59 * hash + this.dayCode;
        hash = 59 * hash + Objects.hashCode(this.startTime);
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
        final CrnSessionTimetablePK other = (CrnSessionTimetablePK) obj;
        if (this.dayCode != other.dayCode) {
            return false;
        }
        if (!Objects.equals(this.crnDetailPK, other.crnDetailPK)) {
            return false;
        }
        return Objects.equals(this.startTime, other.startTime);
    }
    
}
