/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.StudentStudyplanPK;
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
 * Represents student_studyplan table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "student_studyplan", schema = "admin_it_studies_dev")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentStudyplan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private StudentStudyplanPK studentStudyplanPK;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("studentID")
    @JoinColumn(name = "StudentID")
    @ToString.Exclude
    private Student student;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("qualCode")
    @JoinColumn(name = "QualCode")
    @ToString.Exclude
    private Qualification qualification;
    
    @Basic(optional = false)
    @Column(name = "EnrolmentType")
    private String enrolmentType;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.studentStudyplanPK);
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
        final StudentStudyplan other = (StudentStudyplan) obj;
        if (!Objects.equals(this.studentStudyplanPK, other.studentStudyplanPK)) {
            return false;
        }
        return true;
    }
    
}
