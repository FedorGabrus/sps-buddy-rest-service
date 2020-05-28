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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity class that represents student_studyplan table.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 27/05/2020
 */
@Entity
@Table(name = "student_studyplan")
public class StudentStudyplan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected StudentStudyplanPK studentStudyplanPK;
    
    @Basic(optional = false)
    @Column(name = "EnrolmentType")
    private String enrolmentType;
    
    @JoinColumn(name = "QualCode", referencedColumnName = "QualCode", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Qualification qualification;
    
    @JoinColumn(name = "StudentID", referencedColumnName = "StudentID", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;
    
    @JoinColumns({
        @JoinColumn(name = "TermCodeStart", referencedColumnName = "TermCode"),
        @JoinColumn(name = "TermYearStart", referencedColumnName = "TermYear")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TermDatetime termDatetime;

    public StudentStudyplan() {
    }

    public StudentStudyplan(StudentStudyplanPK studentStudyplanPK) {
        this.studentStudyplanPK = studentStudyplanPK;
    }

    public StudentStudyplan(StudentStudyplanPK studentStudyplanPK, String enrolmentType) {
        this.studentStudyplanPK = studentStudyplanPK;
        this.enrolmentType = enrolmentType;
    }

    public StudentStudyplan(String studentID, String qualCode) {
        this.studentStudyplanPK = new StudentStudyplanPK(studentID, qualCode);
    }

    public StudentStudyplanPK getStudentStudyplanPK() {
        return studentStudyplanPK;
    }

    public void setStudentStudyplanPK(StudentStudyplanPK studentStudyplanPK) {
        this.studentStudyplanPK = studentStudyplanPK;
    }

    public String getEnrolmentType() {
        return enrolmentType;
    }

    public void setEnrolmentType(String enrolmentType) {
        this.enrolmentType = enrolmentType;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TermDatetime getTermDatetime() {
        return termDatetime;
    }

    public void setTermDatetime(TermDatetime termDatetime) {
        this.termDatetime = termDatetime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.studentStudyplanPK);
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
