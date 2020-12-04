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

package au.edu.tafesa.spsbuddyrestservice.model;

import au.edu.tafesa.spsbuddyrestservice.controller.TermDateTimeController;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Used to transfer Term Date Time data.
 *
 * @author Fedor Gabrus
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "TermDateTimes", itemRelation = "TermDateTime")
public class TermDateTimeDTO extends RepresentationModel<TermDateTimeDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int termCode;
    private final int termYear;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int semesterCode;

    public TermDateTimeDTO(int termCode, int termYear, @NonNull LocalDate startDate, @NonNull LocalDate endDate,
                           int semesterCode) {
        super(linkTo(methodOn(TermDateTimeController.class).getOneTermDateTime(termYear, termCode)).withSelfRel());

        this.termCode = termCode;
        this.termYear = termYear;
        this.startDate = startDate;
        this.endDate = endDate;
        this.semesterCode = semesterCode;
    }
}
