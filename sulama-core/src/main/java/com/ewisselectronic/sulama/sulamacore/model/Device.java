package com.ewisselectronic.sulama.sulamacore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Device {

    @Id
    private Long deviceId;
    private String deviceName;
    private Integer relayCount;
    private Date deviceActivationDate;
    private Double locationLatitude;
    private Double locationLongitude;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

}
