package com.ewisselectronic.sulama.sulamacore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class IrrigationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IRRIGATION_TASK_SEQ")
    @SequenceGenerator(name="IRRIGATION_TASK_SEQ", sequenceName="IRRIGATION_TASK_SEQ", allocationSize = 0)
    private Long taskId;

    private Date startDate;
    private Date endDate;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Task_Relay",
            joinColumns = { @JoinColumn(name = "taskId") },
            inverseJoinColumns = { @JoinColumn(name = "relayId") }
    )
    Set<Relay> relays = new HashSet<>();
}
