package com.uniovi.sdi2324310spring.repositories;

import com.uniovi.sdi2324310spring.entities.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;

public interface MarksRepository extends CrudRepository<Mark, Long> {
}
