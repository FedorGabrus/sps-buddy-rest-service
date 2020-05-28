/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class that represents term_datetime table.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 27/05/2020
 */
@Entity
@Table(name = "term_datetime")
public class TermDatetime implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected TermDatetimePK termDatetimePK;
    
    @Basic(optional = false)
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;
    
    @Basic(optional = false)
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;
    
    @Basic(optional = false)
    @Column(name = "SemesterCode")
    private int semesterCode;

    public TermDatetime() {
    }

    public TermDatetime(TermDatetimePK termDatetimePK) {
        this.termDatetimePK = termDatetimePK;
    }

    public TermDatetime(TermDatetimePK termDatetimePK, LocalDate startDate, LocalDate endDate, int semesterCode) {
        this.termDatetimePK = termDatetimePK;
        this.startDate = startDate;
        this.endDate = endDate;
        this.semesterCode = semesterCode;
    }

    public TermDatetime(int termCode, int termYear) {
        this.termDatetimePK = new TermDatetimePK(termCode, termYear);
    }

    public TermDatetimePK getTermDatetimePK() {
        return termDatetimePK;
    }

    public void setTermDatetimePK(TermDatetimePK termDatetimePK) {
        this.termDatetimePK = termDatetimePK;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.termDatetimePK);
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
        final TermDatetime other = (TermDatetime) obj;
        return Objects.equals(this.termDatetimePK, other.termDatetimePK);
    }

    @Override
    public String toString() {
        return "TermDatetime{" + "termDatetimePK=" + termDatetimePK + ", startDate=" + startDate + ", endDate=" + endDate + ", semesterCode=" + semesterCode + '}';
    }
    
}
