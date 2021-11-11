package com.example.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ob_companies")
@Audited
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cif;

    private String legalName;

    private Double capital;

    private Integer year;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;


    // ==================== ASOCIACIÓN: ONE TO MANY ==================================================
    @OneToMany(mappedBy = "company")
    List<Employee> employees = new ArrayList<>();

    public Company(){}

    public Company(Long id, String cif, String legalName, Double capital, Integer year) {
        this.id = id;
        this.cif = cif;
        this.legalName = legalName;
        this.capital = capital;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", cif='" + cif + '\'' +
                ", legalName='" + legalName + '\'' +
                ", capital=" + capital +
                ", year=" + year +
                '}';
    }
}



