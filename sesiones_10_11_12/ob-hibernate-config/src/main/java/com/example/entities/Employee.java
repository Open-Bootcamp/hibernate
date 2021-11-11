package com.example.entities;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Representa la tabla ob_employees en base de datos
 */
@Entity
@Table(name="ob_employees") // opcional
// @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
//@Audited
@NamedQuery(name = "Employee.mostPaid", query = "from Employee e where e.salary > 50000")
public class Employee implements Serializable {

    // atributos (representa una columna en la tabla)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private Integer age;

    @Audited
    private Double salary;

    private Boolean married;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "edit_date")
    private LocalDateTime editDate;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @ElementCollection
    private List<String> nickNames = new ArrayList<>();

    @ElementCollection
    private List<Integer> postalCodes = new ArrayList<>();

    @ElementCollection
    private Set<String> creditCards = new HashSet<>();

    @ElementCollection
    private Map<String, String> phones = new HashMap<>();

    // por defecto se almacena ORDINAL (numérico).
    // EnumType.STRING hace que se almacene como texto
    @Enumerated(EnumType.STRING)
    EmployeeCategory category;

    // ==================== ASOCIACIÓN: ONE TO ONE ==================================================
    // 1. Asociación clave foránea: se crea una nueva columna en la tabla employees
    // @OneToOne(cascade = CascadeType.ALL)
    @OneToOne
    @JoinColumn(name = "direction_pk",
            foreignKey = @ForeignKey(name = "fk_employee_direction")) // permite cambiar el nombre de la columna y de la fk

    // 2. Asociación con tabla
//    @OneToOne
//    @JoinTable(name = "ob_employee_direction",
//            joinColumns = @JoinColumn(name="employee_id"),
//            inverseJoinColumns = @JoinColumn(name="direction_id") )

    // 3. Asociación Clave primaria
//    @OneToOne
//    @PrimaryKeyJoinColumn

    // 4. MapsId
//    @OneToOne
//    @MapsId
    Direction direction;

    // ==================== ASOCIACIÓN: ONE TO MANY ==================================================
    @OneToMany
//    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "ob_employee_cars",
        joinColumns = @JoinColumn(name="employee_id"),
        inverseJoinColumns = @JoinColumn(name="car_id") )
    List<Car> cars = new ArrayList<>();

    // ==================== ASOCIACIÓN: MANY TO ONE ==================================================
    @ManyToOne(cascade = CascadeType.ALL)
    Company company;

    // ==================== ASOCIACIÓN: MANY TO MANY ==================================================
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ob_employee_projects",
            joinColumns = @JoinColumn(name="employee_id"),
            inverseJoinColumns = @JoinColumn(name="project_id") )
    List<Project> projects = new ArrayList<>();


    // constructores
    public Employee(){}

    public Employee(Long id, String firstName, String lastName, String email, Integer age, Double salary, Boolean married, LocalDate birthDate, LocalDateTime registerDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.salary = salary;
        this.married = married;
        this.birthDate = birthDate;
        this.registerDate = registerDate;
    }

// getter y setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public List<String> getNickNames() {
        return nickNames;
    }

    public void setNickNames(List<String> nickNames) {
        this.nickNames = nickNames;
    }

    public List<Integer> getPostalCodes() {
        return postalCodes;
    }

    public void setPostalCodes(List<Integer> postalCodes) {
        this.postalCodes = postalCodes;
    }

    public Set<String> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<String> creditCards) {
        this.creditCards = creditCards;
    }

    public Map<String, String> getPhones() {
        return phones;
    }

    public void setPhones(Map<String, String> phones) {
        this.phones = phones;
    }

    public EmployeeCategory getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategory category) {
        this.category = category;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", married=" + married +
                ", birthDate=" + birthDate +
                ", registerDate=" + registerDate +
                '}';
    }

    // ====== EVENTOS

    /**
     * Método que se ejecuta antes de insertar una entidad Employee en base de datos
     * Antes de crear.
     */
    @PrePersist
    public void prePersist() {
        System.out.println("prePersist");
        this.setRegisterDate(LocalDateTime.now());
        this.setEditDate(LocalDateTime.now());
    }
    @PreUpdate
    public void preUpdate() {
        System.out.println("preUpdate");
        this.setEditDate(LocalDateTime.now());
    }
    @PreRemove
    public void preRemove() {
        System.out.println("preRemove");
        // this.setCars(new ArrayList<>());
    }


}
