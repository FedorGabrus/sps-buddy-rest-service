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

import au.edu.tafesa.spsbuddyrestservice.controller.SubjectController;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Used to transfer subject data.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "subjects", itemRelation = "subject")
public class SubjectDTO extends RepresentationModel<SubjectDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String subjectCode;
    private final String subjectDescription;
    private final List<SubjectDTO> prerequisites;

    public SubjectDTO(@NonNull String subjectCode, @NonNull String subjectDescription, List<SubjectDTO> prerequisites) {
        super(linkTo(methodOn(SubjectController.class).getOneSubject(subjectCode)).withSelfRel());

        this.subjectCode = subjectCode;
        this.subjectDescription = subjectDescription;
        this.prerequisites = prerequisites;
    }
}
