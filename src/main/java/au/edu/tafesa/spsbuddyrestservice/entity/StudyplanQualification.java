/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class that represents studyplan_qualification table.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 28/05/2020
 */
@Entity
@Table(name = "studyplan_qualification")
public class StudyplanQualification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "StudyPlanCode")
    private String studyPlanCode;
    
    @Basic(optional = false)
    @Column(name = "Priority")
    private int priority;
    
    // TODO: Delete this relation if TimingTerm in StudyplanSubject is nullable.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studyplanQualification", fetch = FetchType.LAZY)
    private List<StudyplanSubject> studyplanSubjectList;
    
    @JoinColumn(name = "QualCode", referencedColumnName = "QualCode")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Qualification qualification;

    public StudyplanQualification() {
    }

    public StudyplanQualification(String studyPlanCode) {
        this.studyPlanCode = studyPlanCode;
    }

    public StudyplanQualification(String studyPlanCode, int priority) {
        this.studyPlanCode = studyPlanCode;
        this.priority = priority;
    }

    public String getStudyPlanCode() {
        return studyPlanCode;
    }

    public void setStudyPlanCode(String studyPlanCode) {
        this.studyPlanCode = studyPlanCode;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<StudyplanSubject> getStudyplanSubjectList() {
        return studyplanSubjectList;
    }

    public void setStudyplanSubjectList(List<StudyplanSubject> studyplanSubjectList) {
        this.studyplanSubjectList = studyplanSubjectList;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.studyPlanCode);
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
