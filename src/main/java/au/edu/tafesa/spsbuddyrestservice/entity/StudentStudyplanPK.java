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
 * Represents primary key for the StudentStudyplan entity class.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 27/05/2020
 */
@Embeddable
public class StudentStudyplanPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "StudentID")
    private String studentID;
    
    @Basic(optional = false)
    @Column(name = "QualCode")
    private String qualificationCode;

    public StudentStudyplanPK() {
    }

    public StudentStudyplanPK(String studentID, String qualCode) {
        this.studentID = studentID;
        this.qualificationCode = qualCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getQualificationCode() {
        return qualificationCode;
    }

    public void setQualificationCode(String qualificationCode) {
        this.qualificationCode = qualificationCode;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.studentID);
        hash = 83 * hash + Objects.hashCode(this.qualificationCode);
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
        final StudentStudyplanPK other = (StudentStudyplanPK) obj;
        if (!Objects.equals(this.studentID, other.studentID)) {
            return false;
        }
        return Objects.equals(this.qualificationCode, other.qualificationCode);
    }

    @Override
    public String toString() {
        return "StudentStudyplanPK{" + "studentID=" + studentID + ", qualCode=" + qualificationCode + '}';
    }
    
}
