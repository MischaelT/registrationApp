package com.upscale.registration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attendees")
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendees_generator")
    @SequenceGenerator(name = "attendees_generator", sequenceName = "attendee_seq", allocationSize = 1)
    private long id;
    @Column(name = "attendee_name")
    private String name;
    @Column(name = "linkedin_link")
    private String linkedInLink;
    @Column(name = "facebook_link")
    private String facebookLink;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "current_position")
    private String currentPosition;
    @Column(name = "current_company")
    private String currentCompany;
    @Column(name = "location")
    private String location;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "attendees")
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    public Attendee() {
    }

    public Attendee(String name) {
        this.name = name;
    }

    public Attendee(String name, String linkedInLink, String facebookLink, Set<Event> events) {
        this.name = name;
        this.linkedInLink = linkedInLink;
        this.facebookLink = facebookLink;
        this.events = events;
    }
    public Attendee(String name, String linkedInLink) {
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

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}


