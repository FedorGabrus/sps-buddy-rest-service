/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business.pk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
 * Represents PK for crn_detail table.
 * 
 * @author Fedor Gabrus
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrnDetailPK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "CRN")
    private String crn;
    
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "termCode", column = @Column(name = "TermCodeStart")),
            @AttributeOverride(name = "termYear", column = @Column(name = "TermYearStart"))})
    private TermDatetimePK termDatetimePK;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.crn);
        hash = 13 * hash + Objects.hashCode(this.termDatetimePK);
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
        final CrnDetailPK other = (CrnDetailPK) obj;
        if (!Objects.equals(this.crn, other.crn)) {
            return false;
        }
        return Objects.equals(this.termDatetimePK, other.termDatetimePK);
    }
    
}
