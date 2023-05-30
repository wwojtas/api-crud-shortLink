package com.example.shortener.link;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
class Link {
    @Id
    private String id;
    private String name;
    private String password;
    private String targetUrl;
    private int visits;

    public Link() { }

    public Link(String id, String name, String password, String targetUrl) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.targetUrl = targetUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
