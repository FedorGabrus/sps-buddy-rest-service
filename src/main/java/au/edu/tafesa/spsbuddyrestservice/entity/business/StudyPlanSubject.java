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

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.StudyPlanSubjectPK;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents studyplan_subject table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "studyplan_subject")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudyPlanSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private StudyPlanSubjectPK studyplanSubjectPK;

    @Basic
    @Column(name = "StudyPlanCode", insertable = false, updatable = false)
    private String studyPlanCode;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("studyPlanCode")
    @JoinColumn(name = "StudyPlanCode")
    @ToString.Exclude
    private StudyPlanQualification studyplanQualification;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("subjectCode")
    @JoinColumn(name = "SubjectCode")
    @ToString.Exclude
    private Subject subject;
    
    @Basic
    @Column(name = "TimingStage")
    private Integer timingStage;
    
    @Basic
    @Column(name = "TimingTerm")
    private Integer timingTerm;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.studyplanSubjectPK);
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
        final StudyPlanSubject other = (StudyPlanSubject) obj;
        return Objects.equals(this.studyplanSubjectPK, other.studyplanSubjectPK);
    }
    
}
