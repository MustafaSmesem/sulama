package com.ewisselectronic.sulama.sulamacore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(indexes = {@Index(columnList = "deleted", name = "INX_DEVICE_deleted")})
public class Device {

    @Id
    private Long deviceId;
    private String deviceName;
    private Integer roleCount;
    private Date deviceActivatedDate;
    private Date deviceLicenceExpiredDate;
    private Double locationLatitude;
    private Double locationLongitude;
    private String deviceLicence;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
