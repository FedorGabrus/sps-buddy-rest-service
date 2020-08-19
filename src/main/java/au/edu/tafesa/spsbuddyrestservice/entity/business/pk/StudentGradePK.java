/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business.pk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents PK for student_grade table.
 * 
 * @author Fedor Gabrus
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentGradePK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "StudentID", insertable = false, updatable = false)
    private String studentID;
    
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "crn", column = @Column(name = "CRN")),
            @AttributeOverride(name = "termDatetimePK.termCode", column = @Column(name = "TermCode")),
            @AttributeOverride(name = "termDatetimePK.termYear", column = @Column(name = "TermYear"))})
    private CrnDetailPK crnDetailPK;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.studentID);
        hash = 73 * hash + Objects.hashCode(this.crnDetailPK);
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
        final StudentGradePK other = (StudentGradePK) obj;
        if (!Objects.equals(this.studentID, other.studentID)) {
            return false;
        }
        return Objects.equals(this.crnDetailPK, other.crnDetailPK);
    }
    
}
