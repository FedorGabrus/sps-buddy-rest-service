/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.StudyplanSubjectPK;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * Represents studyplan_subject table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "studyplan_subject", schema = "admin_it_studies_dev")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudyplanSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected StudyplanSubjectPK studyplanSubjectPK;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("studyPlanCode")
    @JoinColumn(name = "StudyPlanCode")
    @ToString.Exclude
    private StudyplanQualification studyplanQualification;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("subjectCode")
    @JoinColumn(name = "SubjectCode")
    @ToString.Exclude
    private Subject subject;
    
    @Basic
    @Column(name = "TimingStage")
    private Integer timingStage;
    
    @Basic
    @Column(name = "TimingTerm")
    private Integer timingTerm;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.studyplanSubjectPK);
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
        final StudyplanSubject other = (StudyplanSubject) obj;
        return Objects.equals(this.studyplanSubjectPK, other.studyplanSubjectPK);
    }
    
}
