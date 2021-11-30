package com.example.obhibernateproyecto.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Task: representa una entidad tarea, con los atributos id, título, descripción,
 * finalizada (sí o no), fecha de entrega (LocalDate)
 */
@Entity
@Table(name = "ob_tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @Column(length = 300)
    private String description;

    private Boolean finished;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Task() {
    }

    public Task(Long id, String title, String description, Boolean finished, LocalDate deliveryDate, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.deliveryDate = deliveryDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", finished=" + finished +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
