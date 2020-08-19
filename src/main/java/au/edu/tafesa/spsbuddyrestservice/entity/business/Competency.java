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
 * Represents competency table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "competency", schema = "admin_it_studies_dev")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Competency implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "TafeCompCode")
    private String tafeCompCode;
    
    @Basic(optional = false)
    @Column(name = "NationalCompCode", unique = true)
    private String nationalCompCode;
    
    @Basic(optional = false)
    @Column(name = "CompetencyName")
    private String competencyName;
    
    @Basic(optional = false)
    @Column(name = "Hours")
    private int hours;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.tafeCompCode);
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
        final Competency other = (Competency) obj;
        return Objects.equals(this.tafeCompCode, other.tafeCompCode);
    }
    
}
