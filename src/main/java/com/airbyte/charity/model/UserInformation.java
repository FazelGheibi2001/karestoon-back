package com.airbyte.charity.model;

import com.airbyte.charity.permission.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table
public class UserInformation implements Serializable {
    private @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String firstName;
    private @Column(columnDefinition = "VARCHAR(255)") String lastName;
    private @Column(columnDefinition = "VARCHAR(255)", unique = true) String username;
    private @Column(columnDefinition = "VARCHAR(255)") String password;
    private @Column(columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING) Role role;


    public String getId() {
        return id;
    }

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
