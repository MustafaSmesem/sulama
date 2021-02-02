package com.ewisselectronic.sulama.sulamacore.repository;

import com.ewisselectronic.sulama.sulamacore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor {
    User findByUsername(String username);

    @Query(value = "select * from app_user where enabled in (?2) and translate(username||name||surname, 'ıi','Iİ') ilike translate(?1, 'ıi','Iİ')",
            countQuery = "select count(*) from app_user where enabled in (?2) and translate(username||name||surname, 'ıi','Iİ') ilike translate(?1, 'ıi','Iİ')",
            nativeQuery = true)
    Page<User> findByAnyColumnContaining(String text, List<Boolean> statusList, Pageable pageable);

    @Query(value = "select * from app_user where enabled in (?1)",
            countQuery = "select count(*) from app_user where enabled in (?1)",
            nativeQuery = true)
    Page<User> findByEnabled(List<Boolean> statusList, Pageable pageable);

    Page<User> findAllBy(Pageable pageable);
}
