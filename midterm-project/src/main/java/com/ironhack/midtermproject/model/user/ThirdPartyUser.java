package com.ironhack.midtermproject.model.user;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ThirdPartyUser extends User {

    private String hashedKey;

    public ThirdPartyUser() {
    }

    public ThirdPartyUser(String name, String username, String password, String hashedKey) {
        super(name, username, password);
        this.hashedKey = hashedKey;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}



