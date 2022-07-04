package com.ironhack.midtermproject.model.user;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {

    public Admin() {
    }

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }
}