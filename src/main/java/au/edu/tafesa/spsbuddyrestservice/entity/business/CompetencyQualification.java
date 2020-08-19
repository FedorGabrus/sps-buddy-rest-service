/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.CompetencyQualificationPK;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents competency_qualification table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "competency_qualification", schema = "admin_it_studies_dev")
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
    @JoinColumn(name = "QualCode", insertable = false, updatable = false)
    @ToString.Exclude
    private Qualification qualification;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("nationalCompCode")
    @JoinColumn(name = "NationalCompCode", referencedColumnName = "NationalCompCode",
            insertable = false, updatable = false)
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
