/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents primary key for the TermDatetime entity class.
 * 
 * @author Fedor Gabrus
 * @version 1.0.0
 * @since 27/05/2020
 */
@Embeddable
public class TermDatetimePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "TermCode")
    private int termCode;
    
    @Basic(optional = false)
    @Column(name = "TermYear")
    private int termYear;

    public TermDatetimePK() {
    }

    public TermDatetimePK(int termCode, int termYear) {
        this.termCode = termCode;
        this.termYear = termYear;
    }

    public int getTermCode() {
        return termCode;
    }

    public void setTermCode(int termCode) {
        this.termCode = termCode;
    }

    public int getTermYear() {
        return termYear;
    }

    public void setTermYear(int termYear) {
        this.termYear = termYear;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.termCode;
        hash = 29 * hash + this.termYear;
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
        final TermDatetimePK other = (TermDatetimePK) obj;
        if (this.termCode != other.termCode) {
            return false;
        }
        return this.termYear == other.termYear;
    }

    @Override
    public String toString() {
        return "TermDatetimePK{" + "termCode=" + termCode + ", termYear=" + termYear + '}';
    }
    
}
