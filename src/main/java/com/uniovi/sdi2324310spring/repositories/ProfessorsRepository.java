package com.uniovi.sdi2324310spring.repositories;

import com.uniovi.sdi2324310spring.entities.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorsRepository extends CrudRepository<Professor, Long> {
}
