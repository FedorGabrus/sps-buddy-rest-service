/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
@Table(name = "term_datetime", schema = "admin_it_studies_dev")
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
