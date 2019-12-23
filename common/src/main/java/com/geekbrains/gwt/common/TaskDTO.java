package com.geekbrains.gwt.common;

public class TaskDTO {
    private Long id;
    private String caption;
    private String owner;
    private String assigned;
    private String description;
    private Task.Status status;

    public TaskDTO(Long id, String caption, String owner, String assigned, String description, Task.Status status) {
        this.id = id;
        this.caption = caption;
        this.owner = owner;
        this.assigned = assigned;
        this.description = description;
        this.status = status;
    }

    public TaskDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task.Status getStatus() {
        return status;
    }

    public void setStatus(Task.Status status) {
        this.status = status;
    }
}
