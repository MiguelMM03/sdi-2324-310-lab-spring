package com.uniovi.sdi2324310spring.repositories;

import com.uniovi.sdi2324310spring.entities.Mark;
import com.uniovi.sdi2324310spring.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarksRepository extends CrudRepository<Mark, Long> {
    Page<Mark> findAll(Pageable pageable);
    @Query("SELECT r FROM Mark r WHERE r.user = ?1 ORDER BY r.id ASC")
    Page<Mark> findAllByUser(Pageable pageable,User user);
    @Modifying
    @Transactional
    @Query("UPDATE Mark SET resend = ?1 WHERE id = ?2")
    void updateResend(Boolean resend, Long id);

    @Query("SELECT r FROM Mark r WHERE(LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1))")
    Page<Mark> searchByDescriptionAndName(Pageable pageable,String searchtext);

    @Query("SELECT r FROM Mark r WHERE(LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1)) AND r.user=?2")
    Page<Mark> searchByDescriptionNameAndUser(Pageable pageable,String searchText, User user);
}

