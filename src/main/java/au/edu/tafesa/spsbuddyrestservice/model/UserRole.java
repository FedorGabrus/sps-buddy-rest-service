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

/**
 * Stores available user roles.
 * When these values changed corresponding front-end data should be altered as well.
 * 
 * Should contain similar values as the DB table that represents user's roles.
 * Example: ROLE_STUDENT in DB should have corresponding STUDENT constant in this enum.
 * 
 * @author Fedor Gabrus
 */
public enum UserRole {
    STUDENT,
    LECTURER
}