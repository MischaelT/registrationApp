package com.upscale.registration.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    private long user_id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "linked_login")
    private String linkedInLink;

    @Column( name = "linked_password")
    private String linkedPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public String getLinkedPassword() {
        return linkedPassword;
    }

    public void setLinkedPassword(String linkedPassword) {
        this.linkedPassword = linkedPassword;
    }
}
