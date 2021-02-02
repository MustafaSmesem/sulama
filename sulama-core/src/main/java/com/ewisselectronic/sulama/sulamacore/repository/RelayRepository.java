package com.ewisselectronic.sulama.sulamacore.repository;

import com.ewisselectronic.sulama.sulamacore.model.Relay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayRepository extends CrudRepository<Relay, Long> {
}
