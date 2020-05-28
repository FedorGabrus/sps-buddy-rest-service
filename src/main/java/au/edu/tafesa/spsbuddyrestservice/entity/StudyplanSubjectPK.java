/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents primary key for the StudyplanSubject entity class.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 28/05/2020
 */
@Embeddable
public class StudyplanSubjectPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "StudyPlanCode")
    private String studyPlanCode;
    
    @Basic(optional = false)
    @Column(name = "SubjectCode")
    private String subjectCode;

    public StudyplanSubjectPK() {
    }

    public StudyplanSubjectPK(String studyPlanCode, String subjectCode) {
        this.studyPlanCode = studyPlanCode;
        this.subjectCode = subjectCode;
    }

    public String getStudyPlanCode() {
        return studyPlanCode;
    }

    public void setStudyPlanCode(String studyPlanCode) {
        this.studyPlanCode = studyPlanCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.studyPlanCode);
        hash = 37 * hash + Objects.hashCode(this.subjectCode);
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
        final StudyplanSubjectPK other = (StudyplanSubjectPK) obj;
        if (!Objects.equals(this.studyPlanCode, other.studyPlanCode)) {
            return false;
        }
        if (!Objects.equals(this.subjectCode, other.subjectCode)) {
            return false;
        }
        return true;
    }
    
}
