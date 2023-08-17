package com.example.demo.Models;

public class JobDTO {
    private String id;
    private String type;
    private String url;
    private String created_at;

    public JobDTO(String id, String type, String url, String created_at) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.created_at = created_at;
    }

    public JobDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
