package com.ewisselectronic.sulama.sulamacore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "\"app_user\"", indexes = { @Index(columnList = "username", name = "INX_APP_USER_username") })
public class User {

    @Id
    @SequenceGenerator(name="USERS_SEQ", sequenceName="USERS_SEQ", allocationSize=0, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    private Integer id;

    @NotEmpty
    private String name;

    private String surname;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns
            = @JoinColumn(name = "user_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private List<Role> roles;

    private boolean enabled;
    private boolean admin;

    private Date lastInteractionTime;
    private Integer loginCount;

}
