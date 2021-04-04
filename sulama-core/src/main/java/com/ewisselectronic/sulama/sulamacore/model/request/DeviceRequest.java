package com.ewisselectronic.sulama.sulamacore.model.request;

import com.ewisselectronic.sulama.sulamacore.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequest {

    private String deviceId;
    private String deviceName;
    private Integer relayCount;
    private Date deviceActivationDate;
    private Double locationLatitude;
    private Double locationLongitude;
    private int user;

    public DeviceRequest(Device device) {
        deviceId = device.getDeviceId();
        deviceName = device.getDeviceName();
        relayCount = device.getRelayCount();
        deviceActivationDate = device.getDeviceActivationDate();
        user = device.getUser().getId();
        locationLatitude = device.getLocationLatitude();
        locationLongitude = device.getLocationLongitude();
    }
}
