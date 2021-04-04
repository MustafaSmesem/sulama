package com.ewisselectronic.sulama.sulamaservice.controller;

import com.ewisselectronic.sulama.sulamacore.model.Device;
import com.ewisselectronic.sulama.sulamacore.model.Role;
import com.ewisselectronic.sulama.sulamacore.model.User;
import com.ewisselectronic.sulama.sulamacore.model.request.DeviceRequest;
import com.ewisselectronic.sulama.sulamacore.service.DeviceService;
import com.ewisselectronic.sulama.sulamacore.service.UserService;
import com.ewisselectronic.sulama.sulamaservice.model.SaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/api/devices/getAll", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<?> getAllDevices() {
        List<Device> devices = deviceService.getAll();
        devices.forEach(device -> {
            User user = device.getUser();
            user.setPassword("");
            device.setUser(user);
        });
        return ResponseEntity.ok(devices);
    }


    @GetMapping(value = "/api/devices/getByUser/{userId}", produces = "application/json")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public ResponseEntity<?> getAllDevicesByUser(@PathVariable("userId") int userId) {
        List<DeviceRequest> devices = new ArrayList<>();
        deviceService.getDevicesByUser(userId).forEach(device -> {
            devices.add(new DeviceRequest(device));
        });
        return ResponseEntity.ok(devices);
    }


    @PostMapping(value = "/api/devices/save", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public SaveResponse saveDevice(@RequestBody DeviceRequest deviceRequest) {
        try {
            Device device = new Device();
            device.setDeviceId(deviceRequest.getDeviceId());
            device.setDeviceName(deviceRequest.getDeviceName());
            device.setRelayCount(deviceRequest.getRelayCount());
            device.setDeviceActivationDate(deviceRequest.getDeviceActivationDate() != null ? deviceRequest.getDeviceActivationDate() : new Date());
            if (deviceRequest.getLocationLatitude() != null)
                device.setLocationLatitude(deviceRequest.getLocationLatitude());
            if (deviceRequest.getLocationLongitude() != null)
                device.setLocationLongitude(deviceRequest.getLocationLongitude());
            User user = userService.get(deviceRequest.getUser());
            if (user != null)
                device.setUser(user);
            else
                return new SaveResponse(false, String.format("Cannot save device[%s] because it's user doesn't exist", device.getDeviceId()), null);

            device.setDeleted(false);
            deviceService.save(device);
            return new SaveResponse(true, String.format("Device [%s] saved successfully", device.getDeviceId()), null);
        } catch (Exception e) {
            return new SaveResponse(false, e.getMessage(), null);
        }
    }

    @DeleteMapping(value = "/api/devices/delete/{itemId}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public SaveResponse deleteDevice(@PathVariable("itemId") String itemId) {
        try {
            deviceService.delete(itemId);
            return new SaveResponse(true, String.format("Device [%s] deleted successfully", itemId), null);
        } catch (Exception e) {
            return new SaveResponse(false, e.getMessage(), null);
        }
    }

}
