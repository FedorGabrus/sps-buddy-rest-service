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

import au.edu.tafesa.spsbuddyrestservice.entity.business.pk.StudentGradePK;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents student_grade table.
 * 
 * @author Fedor Gabrus
 */
@Entity
@Table(name = "student_grade")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentGrade implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private StudentGradePK studentGradePK;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            optional = false)
    @MapsId("studentID")
    @ToString.Exclude
    private Student student;
    
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("crnDetailPK")
    @ToString.Exclude
    private CrnDetail crnDetail;
    
    @Basic
    @Column(name = "Grade")
    private String grade;
    
    @Basic
    @Column(name = "GradeDate")
    private LocalDate gradeDate;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.studentGradePK);
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
        final StudentGrade other = (StudentGrade) obj;
        return Objects.equals(this.studentGradePK, other.studentGradePK);
    }
    
}
