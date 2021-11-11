package com.example.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ob_cars")
@Audited
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;

    private Double cc;

    @Column(name="release_year")
    private Integer releaseYear;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @ManyToOne
    Employee employee;

    public Car(){}

    public Car(Long id, String manufacturer, Double cc, Integer releaseYear) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.cc = cc;
        this.releaseYear = releaseYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getCc() {
        return cc;
    }

    public void setCc(Double cc) {
        this.cc = cc;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @PreRemove
    public void preRemove() {
        System.out.println("preRemove");
        this.setEmployee(null);
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", cc=" + cc +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
