/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.repository.business;

import au.edu.tafesa.spsbuddyrestservice.entity.business.Student;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Fedor
 */
public interface StudentRepository extends CrudRepository<Student, String> {
    
}
