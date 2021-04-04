package com.ewisselectronic.sulama.sulamacore.service;

import com.ewisselectronic.sulama.sulamacore.model.Device;
import com.ewisselectronic.sulama.sulamacore.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getDevicesByUser(int userId) {
        return deviceRepository.findAllByUserIdAndDeletedFalse(userId);
    }

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public void delete(String itemId) {
        deviceRepository.deleteDeviceById(itemId);
    }

    public List<Device> getAll() {
        return (List<Device>) deviceRepository.findAll();
    }
}
