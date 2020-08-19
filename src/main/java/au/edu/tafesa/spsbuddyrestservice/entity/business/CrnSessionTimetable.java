/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.entity.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.CrnSessionTimetablePK;
import java.io.Serializable;
import java.time.LocalTime;
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
 * Represents crn_session_timetable table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "crn_session_timetable", schema = "admin_it_studies_dev")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrnSessionTimetable implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected CrnSessionTimetablePK crnSessionTimetablePK;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("crnDetailPK")
    @JoinColumns({
            @JoinColumn(name = "CRN"),
            @JoinColumn(name = "TermCodeStart"),
            @JoinColumn(name = "TermYearStart")})
    @ToString.Exclude
    private CrnDetail crnDetail;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("dayCode")
    @JoinColumn(name = "DayCode")
    @ToString.Exclude
    private DayOfWeek dayOfWeek;
    
    @Basic(optional = false)
    @Column(name = "EndTime")
    private LocalTime endTime;
    
    @Basic
    @Column(name = "Room")
    private String room;
    
    @Basic
    @Column(name = "Building")
    private String building;
    
}
