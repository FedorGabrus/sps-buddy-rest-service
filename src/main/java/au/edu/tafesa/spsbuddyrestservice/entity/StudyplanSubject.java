/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity class that represents studyplan_subject table.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 28/05/2020
 */
@Entity
@Table(name = "studyplan_subject")
public class StudyplanSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected StudyplanSubjectPK studyplanSubjectPK;
    
    @Column(name = "TimingStage")
    private Integer timingStage;
    
    @Column(name = "TimingTerm")
    private Integer timingTerm;
    
    @JoinColumn(name = "StudyPlanCode", referencedColumnName = "StudyPlanCode", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudyplanQualification studyplanQualification;
    
//    @JoinColumn(name = "SubjectCode", referencedColumnName = "SubjectCode", insertable = false, updatable = false)
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    private Subject subject;

    public StudyplanSubject() {
    }

    public StudyplanSubject(StudyplanSubjectPK studyplanSubjectPK) {
        this.studyplanSubjectPK = studyplanSubjectPK;
    }

    public StudyplanSubject(String studyPlanCode, String subjectCode) {
        this.studyplanSubjectPK = new StudyplanSubjectPK(studyPlanCode, subjectCode);
    }

    public StudyplanSubjectPK getStudyplanSubjectPK() {
        return studyplanSubjectPK;
    }

    public void setStudyplanSubjectPK(StudyplanSubjectPK studyplanSubjectPK) {
        this.studyplanSubjectPK = studyplanSubjectPK;
    }

    public Integer getTimingStage() {
        return timingStage;
    }

    public void setTimingStage(Integer timingStage) {
        this.timingStage = timingStage;
    }

    public Integer getTimingTerm() {
        return timingTerm;
    }

    public void setTimingTerm(Integer timingTerm) {
        this.timingTerm = timingTerm;
    }

    public StudyplanQualification getStudyplanQualification() {
        return studyplanQualification;
    }

    public void setStudyplanQualification(StudyplanQualification studyplanQualification) {
        this.studyplanQualification = studyplanQualification;
    }

//    public Subject getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.studyplanSubjectPK);
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
        if (!Objects.equals(this.studyplanSubjectPK, other.studyplanSubjectPK)) {
            return false;
        }
        return true;
    }
    
}
