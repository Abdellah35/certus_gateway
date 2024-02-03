package com.softedge.solution.models;

import javax.persistence.*;

@Entity
@Table(name="permission")
public class Permission {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "permission_name")
    private String permissionName;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserRegistration userRegistration;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }

    public void setUserRegistration(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }
}
