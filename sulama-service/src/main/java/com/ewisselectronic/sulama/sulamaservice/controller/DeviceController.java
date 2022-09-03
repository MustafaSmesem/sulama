package com.ewisselectronic.sulama.sulamaservice.controller;

import com.ewisselectronic.sulama.sulamacore.model.Device;
import com.ewisselectronic.sulama.sulamacore.model.User;
import com.ewisselectronic.sulama.sulamacore.model.request.DeviceRequest;
import com.ewisselectronic.sulama.sulamacore.service.DeviceService;
import com.ewisselectronic.sulama.sulamacore.service.UserService;
import com.ewisselectronic.sulama.sulamaservice.model.SaveResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final UserService userService;


    @GetMapping(value = "/all", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<?> getAllDevices() {
        List<Device> devices = deviceService.getAll();
        List<DeviceRequest> resultDevices = new ArrayList<>();
        devices.forEach(device -> {
            resultDevices.add(new DeviceRequest(device));
        });
        return ResponseEntity.ok(resultDevices);
    }


    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> getAllDevicesByUser(@RequestAttribute("userId") int userId) {
        List<DeviceRequest> devices = new ArrayList<>();
        deviceService.getDevicesByUser(userId).forEach(device -> {
            devices.add(new DeviceRequest(device));
        });
        return ResponseEntity.ok(devices);
    }


    @PostMapping(value = "", produces = "application/json")
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
            User user = userService.get(deviceRequest.getUserId());
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

    @DeleteMapping(value = "/{deviceId}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public SaveResponse deleteDevice(@PathVariable("deviceId") String deviceId) {
        try {
            deviceService.delete(deviceId);
            return new SaveResponse(true, String.format("Device [%s] deleted successfully", deviceId), null);
        } catch (Exception e) {
            return new SaveResponse(false, e.getMessage(), null);
        }
    }

}
