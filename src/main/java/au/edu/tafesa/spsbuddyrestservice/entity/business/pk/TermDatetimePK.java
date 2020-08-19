/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business.pk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents PK for term_datetime table.
 * 
 * @author Fedor Gabrus
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TermDatetimePK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "TermCode")
    private Integer termCode;
    
    @Basic(optional = false)
    @Column(name = "TermYear")
    private Integer termYear;
    
}
