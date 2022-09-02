package com.ewisselectronic.sulama.sulamacore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Device {

    @Id
    private String deviceId;
    private String deviceName;
    private Integer relayCount;
    private Date deviceActivationDate;
    private Double locationLatitude;
    private Double locationLongitude;
    private boolean deleted;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

}
