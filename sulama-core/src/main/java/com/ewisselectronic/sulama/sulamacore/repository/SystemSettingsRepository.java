package com.ewisselectronic.sulama.sulamacore.repository;

import com.ewisselectronic.sulama.sulamacore.model.SystemSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingsRepository extends CrudRepository<SystemSettings, String> {
    SystemSettings findByTag(String tag);

    Page<SystemSettings> findAllBy(Pageable pageable);

    @Query(value = "select * from systemSettings where translate(tag||info||value, 'ıi','Iİ') ilike translate(?1, 'ıi','Iİ')",
            countQuery = "select count(*) from systemSettings where translate(tag||info||value, 'ıi','Iİ') ilike translate(?1, 'ıi','Iİ')",
            nativeQuery = true)
    Page<SystemSettings> findByAnyColumnContaining(String text, Pageable pageable);


}
