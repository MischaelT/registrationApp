package com.upscale.registration.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_generator")
    @SequenceGenerator(name = "events_generator", sequenceName = "events_seq", allocationSize = 1)
    private long id;
    @Column(name = "name")
    private String name;

    @Column( name = "date")
    private Date date;
    @Column(name = "linkedin_link")
    private String linkedInLink;
    @Column(name = "is_passed")
    private boolean isPassed;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "event_users",
            joinColumns = { @JoinColumn(name = "event_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();

    public Event() {
    }

    public Event(String name, Date date, String linkedInLink, boolean isPassed) {
        this.name = name;
        this.date = date;
        this.linkedInLink = linkedInLink;
        this.isPassed = isPassed;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public long getId() {
        return id;
    }

    public boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }
}
