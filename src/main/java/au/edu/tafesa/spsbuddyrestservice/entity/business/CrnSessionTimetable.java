/*
 * Copyright 2020 TAFE SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
@Table(name = "crn_session_timetable")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrnSessionTimetable implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private CrnSessionTimetablePK crnSessionTimetablePK;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("crnDetailPK")
    @ToString.Exclude
    private CrnDetail crnDetail;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("dayCode")
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
