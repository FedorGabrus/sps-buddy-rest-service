/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.StudentGradePK;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents student_grade table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "student_grade", schema = "admin_it_studies_dev")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentGrade implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private StudentGradePK studentGradePK;
    
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
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("crnDetailPK")
    @JoinColumns({
            @JoinColumn(name = "CRN"),
            @JoinColumn(name = "TermCode"),
            @JoinColumn(name = "TermYear")})
    @ToString.Exclude
    private CrnDetail crnDetail;
    
    @Basic
    @Column(name = "Grade")
    private String grade;
    
    @Basic
    @Column(name = "GradeDate")
    private LocalDate gradeDate;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.studentGradePK);
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
        final StudentGrade other = (StudentGrade) obj;
        return Objects.equals(this.studentGradePK, other.studentGradePK);
    }
    
}
