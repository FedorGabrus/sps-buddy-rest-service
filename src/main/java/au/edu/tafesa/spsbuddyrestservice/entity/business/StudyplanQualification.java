/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents studyplan_qualification table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "studyplan_qualification", schema = "admin_it_studies_dev")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudyplanQualification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "StudyPlanCode")
    private String studyPlanCode;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @JoinColumn(name = "QualCode")
    @ToString.Exclude
    private Qualification qualification;
    
    @Basic(optional = false)
    @Column(name = "Priority")
    private int priority;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.studyPlanCode);
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
        final StudyplanQualification other = (StudyplanQualification) obj;
        return Objects.equals(this.studyPlanCode, other.studyPlanCode);
    }
    
}
