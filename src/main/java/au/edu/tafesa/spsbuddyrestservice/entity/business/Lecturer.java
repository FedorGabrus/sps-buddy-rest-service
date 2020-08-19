/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents lecturer table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "lecturer", schema = "admin_it_studies_dev")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Lecturer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "LecturerID")
    private String lecturerID;
    
    @Basic(optional = false)
    @Column(name = "GivenName")
    private String firstName;
    
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    
    @Basic(optional = false)
    @Column(name = "EmailAddress")
    private String emailAddress;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.lecturerID);
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
        final Lecturer other = (Lecturer) obj;
        return Objects.equals(this.lecturerID, other.lecturerID);
    }
    
}
