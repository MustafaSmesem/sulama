package com.ewisselectronic.sulama.sulamacore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"app_role\"")
public class Role {

    public Role(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="role_name")
    private String roleName;

    @Column(name="description")
    private String description;
}
