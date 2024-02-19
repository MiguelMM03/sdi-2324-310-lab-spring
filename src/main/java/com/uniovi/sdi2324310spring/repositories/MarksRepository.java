package com.uniovi.sdi2324310spring.repositories;

import com.uniovi.sdi2324310spring.entities.Mark;
import com.uniovi.sdi2324310spring.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarksRepository extends CrudRepository<Mark, Long> {
    @Query("SELECT r FROM Mark r WHERE r.user = ?1 ORDER BY r.id ASC")
    List<Mark> findAllByUser(User user);
    @Modifying
    @Transactional
    @Query("UPDATE Mark SET resend = ?1 WHERE id = ?2")
    void updateResend(Boolean resend, Long id);
}
