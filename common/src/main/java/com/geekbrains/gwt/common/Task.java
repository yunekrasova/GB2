package com.geekbrains.gwt.common;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    public enum Status {
        CREATED("Создана", 0),
        ASSIGNED("Назначена", 1),
        INPROGRESS("В работе", 2),
        COMPLETED("Закрыта", 3),
        REJECTED("Отклонена", 10);
        private String russianTitle;
        private int sortOrder;

        public String getRussianTitle() {
            return russianTitle;
        }

        public int getSortOrder() {
            return sortOrder;
        }

        Status(String russianTitle, int sortOrder) {
            this.russianTitle = russianTitle;
            this.sortOrder = sortOrder;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskId")
    @SequenceGenerator(name = "taskId", sequenceName = "taskid", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "caption")
    private String caption;

    @Column(name = "owner")
    private String owner;

    @Column(name = "assigned")
    private String assigned;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(javax.persistence.EnumType.STRING)
    private Status status;

    public Long getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getOwner() {
        return owner;
    }

    public String getAssigned() {
        return assigned;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task() {
    }

    public TaskDTO toDto() {
        return new TaskDTO(
                this.id,
                this.caption,
                this.owner,
                this.assigned,
                this.description,
                this.status
            );
    }
}

