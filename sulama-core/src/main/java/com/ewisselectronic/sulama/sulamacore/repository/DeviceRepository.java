package com.ewisselectronic.sulama.sulamacore.repository;

import com.ewisselectronic.sulama.sulamacore.model.Device;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String> {

    List<Device> findAllByUserIdAndDeletedFalse(Integer userId);

    @Modifying
    @Transactional
    @Query("update Device d set d.deleted = true where d.deviceId = :itemId")
    void deleteDeviceById(@Param(value = "itemId")String itemId);

    List<Device> findAllByDeletedFalse();
}
