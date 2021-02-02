package com.ewisselectronic.sulama.sulamacore.repository;

import com.ewisselectronic.sulama.sulamacore.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
}
