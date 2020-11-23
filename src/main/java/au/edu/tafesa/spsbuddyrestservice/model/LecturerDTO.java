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

import au.edu.tafesa.spsbuddyrestservice.controller.LecturerController;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Used to transfer lecturer's data.
 *
 * @author Fedor Gabrus
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "lecturers", itemRelation = "lecturer")
public class LecturerDTO extends RepresentationModel<LecturerDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public LecturerDTO(@NonNull String id, @NonNull String firstName, @NonNull String lastName,
                       @NonNull String email) {
        super(linkTo(methodOn(LecturerController.class).getOneLecturer(id)).withSelfRel());

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
