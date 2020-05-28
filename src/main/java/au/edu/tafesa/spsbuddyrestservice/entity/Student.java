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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class that represents student table.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 27/05/2020
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "StudentID")
    private String studentID;
    
    @Basic(optional = false)
    @Column(name = "GivenName")
    private String firstName;
    
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    
    @Basic(optional = false)
    @Column(name = "EmailAddress")
    private String email;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = FetchType.LAZY)
    private List<StudentStudyplan> studentStudyplanList;

    public Student() {
    }

    public Student(String studentID) {
        this.studentID = studentID;
    }

    public Student(String studentID, String givenName, String lastName, String emailAddress) {
        this.studentID = studentID;
        this.firstName = givenName;
        this.lastName = lastName;
        this.email = emailAddress;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<StudentStudyplan> getStudentStudyplanList() {
        return studentStudyplanList;
    }

    public void setStudentStudyplanList(List<StudentStudyplan> studentStudyplanList) {
        this.studentStudyplanList = studentStudyplanList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.studentID);
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
        final Student other = (Student) obj;
        return Objects.equals(this.studentID, other.studentID);
    }

    @Override
    public String toString() {
        return "Student{" + "studentID=" + studentID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + '}';
    }
    
}
