package com.upscale.registration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    private long id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "linkedin_link")
    private String linkedInLink;
    @Column(name = "facebook_link")
    private String facebookLink;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "users")
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    public User() {
    }

    public User(String name, String linkedInLink, String facebookLink, Set<Event> events) {
        this.name = name;
        this.linkedInLink = linkedInLink;
        this.facebookLink = facebookLink;
        this.events = events;
    }
    public User(String name, String linkedInLink) {
        this.name = name;
        this.linkedInLink = linkedInLink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
