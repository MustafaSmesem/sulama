package com.ewisselectronic.sulama.sulamacore.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(indexes = { @Index(columnList = "tag", name = "INX_SYSTEM_SETTINGS_tag") })
public class SystemSettings {
    @Id
    private String tag;
    @Column(length = 1024)
    private String info;
    private String value;
}
