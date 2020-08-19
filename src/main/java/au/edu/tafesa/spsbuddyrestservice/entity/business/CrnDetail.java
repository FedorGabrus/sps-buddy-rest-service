/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.CrnDetailPK;
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
 * Represents crn_detail table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "crn_detail", schema = "admin_it_studies_dev")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrnDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected CrnDetailPK crnDetailPK;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("termDatetimePK")
    @JoinColumns({
            @JoinColumn(name = "TermCodeStart"),
            @JoinColumn(name = "TermYearStart")})
    @ToString.Exclude
    private TermDatetime termDatetime;
    
    @Basic(optional = false)
    @Column(name = "TermCodeEnd")
    private int termCodeEnd;
    
    @Basic(optional = false)
    @Column(name = "TermYearEnd")
    private int termYearEnd;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @JoinColumn(name = "CampusCode")
    @ToString.Exclude
    private Campus campus;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @JoinColumn(name = "LecturerID")
    @ToString.Exclude
    private Lecturer lecturer;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @JoinColumn(name = "DepartmentCode")
    @ToString.Exclude
    private Department department;
    
    @Basic(optional = false)
    @Column(name = "FreezeDate")
    private LocalDate freezeDate;
    
    @Basic(optional = false)
    @Column(name = "DateCreated")
    private LocalDate dateCreated;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @JoinColumns({
            @JoinColumn(name = "SubjectCode"),
            @JoinColumn(name = "TafeCompCode")})
    @ToString.Exclude
    private SubjectCompetency subjectCompetency;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.crnDetailPK);
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
        final CrnDetail other = (CrnDetail) obj;
        return Objects.equals(this.crnDetailPK, other.crnDetailPK);
    }
    
}
