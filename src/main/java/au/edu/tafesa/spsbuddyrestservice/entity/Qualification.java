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
 * Entity class that represents qualification table.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 27/05/2020
 */
@Entity
@Table(name = "qualification")
public class Qualification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "QualCode")
    private String qualificationCode;
    
    @Basic(optional = false)
    @Column(name = "NationalQualCode")
    private String nationalQualCode;
    
    @Basic(optional = false)
    @Column(name = "TafeQualCode")
    private String tafeQualCode;
    
    @Basic(optional = false)
    @Column(name = "QualName")
    private String qualName;
    
    @Basic(optional = false)
    @Column(name = "TotalUnits")
    private int totalUnits;
    
    @Basic(optional = false)
    @Column(name = "CoreUnits")
    private int coreUnits;
    
    @Basic(optional = false)
    @Column(name = "ElectedUnits")
    private int electedUnits;
    
    @Basic(optional = false)
    @Column(name = "ReqListedElectedUnits")
    private int reqListedElectedUnits;
        
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "qualification", fetch = FetchType.LAZY)
    private List<StudyplanQualification> studyplanQualificationList;

    public Qualification() {
    }

    public Qualification(String qualCode) {
        this.qualificationCode = qualCode;
    }

    public Qualification(String qualCode, String nationalQualCode, String tafeQualCode, String qualName, int totalUnits, int coreUnits, int electedUnits, int reqListedElectedUnits) {
        this.qualificationCode = qualCode;
        this.nationalQualCode = nationalQualCode;
        this.tafeQualCode = tafeQualCode;
        this.qualName = qualName;
        this.totalUnits = totalUnits;
        this.coreUnits = coreUnits;
        this.electedUnits = electedUnits;
        this.reqListedElectedUnits = reqListedElectedUnits;
    }

    public String getQualificationCode() {
        return qualificationCode;
    }

    public void setQualificationCode(String qualificationCode) {
        this.qualificationCode = qualificationCode;
    }

    public String getNationalQualCode() {
        return nationalQualCode;
    }

    public void setNationalQualCode(String nationalQualCode) {
        this.nationalQualCode = nationalQualCode;
    }

    public String getTafeQualCode() {
        return tafeQualCode;
    }

    public void setTafeQualCode(String tafeQualCode) {
        this.tafeQualCode = tafeQualCode;
    }

    public String getQualName() {
        return qualName;
    }

    public void setQualName(String qualName) {
        this.qualName = qualName;
    }

    public int getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(int totalUnits) {
        this.totalUnits = totalUnits;
    }

    public int getCoreUnits() {
        return coreUnits;
    }

    public void setCoreUnits(int coreUnits) {
        this.coreUnits = coreUnits;
    }

    public int getElectedUnits() {
        return electedUnits;
    }

    public void setElectedUnits(int electedUnits) {
        this.electedUnits = electedUnits;
    }

    public int getReqListedElectedUnits() {
        return reqListedElectedUnits;
    }

    public void setReqListedElectedUnits(int reqListedElectedUnits) {
        this.reqListedElectedUnits = reqListedElectedUnits;
    }

    public List<StudyplanQualification> getStudyplanQualificationList() {
        return studyplanQualificationList;
    }

    public void setStudyplanQualificationList(List<StudyplanQualification> studyplanQualificationList) {
        this.studyplanQualificationList = studyplanQualificationList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.qualificationCode);
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
        final Qualification other = (Qualification) obj;
        return Objects.equals(this.qualificationCode, other.qualificationCode);
    }

    @Override
    public String toString() {
        return "Qualification{" + "qualCode=" + qualificationCode + ", nationalQualCode=" + nationalQualCode + ", tafeQualCode=" + tafeQualCode + ", qualName=" + qualName + ", totalUnits=" + totalUnits + ", coreUnits=" + coreUnits + ", electedUnits=" + electedUnits + ", reqListedElectedUnits=" + reqListedElectedUnits + '}';
    }
    
}
