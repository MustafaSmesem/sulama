package com.ewisselectronic.sulama.sulamacore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Relay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELAY_SEQ")
    @SequenceGenerator(name="RELAY_SEQ", sequenceName="RELAY_SEQ", allocationSize = 0)
    private Long relayId;
    private String relayName;
    private Boolean hasHumiditySensor;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "deviceId")
    private Device device;

    @ManyToMany(mappedBy = "relays")
    private Set<IrrigationTask> tasks = new HashSet<>();
}
