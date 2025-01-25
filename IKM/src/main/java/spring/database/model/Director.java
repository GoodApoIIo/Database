package spring.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "directors")
public class Director {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank(message = "Surname is mandatory")
    @Size(max = 50, message = "Surname must be less than 50 characters")
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name must be less than 255 characters")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull(message = "Alive is mandatory")
    @Column(name = "is_alive", nullable = false)
    private Boolean isAlive = true;


    @ManyToMany(mappedBy = "directors")
    private Set<Film> films;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean newIsAlive) {
        this.isAlive = newIsAlive;
    }
}