/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business.pk;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
