/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
@Table(name = "qualification", schema = "admin_it_studies_dev")
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
