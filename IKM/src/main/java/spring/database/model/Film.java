package spring.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Genre is mandatory")
    @Column(name = "genre_id")
    private Integer genreId;

    @PastOrPresent(message = "Publication date must be in the past or present")
    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @DecimalMin(value = "0.0", message = "Popularity score must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Popularity score must be at most 10.0")
    @Column(name = "popularity_score")
    private Float popularityScore;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Min(value = 0, message = "Budget must be at least 0")
    @Column(name = "budget")
    private int budget;

    @ManyToMany
    @JoinTable(
            name = "film_directors",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Float getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(Float popularityScore) {
        this.popularityScore = popularityScore;
    }

    public String getCreatedAt() {
        if (createdAt != null) {return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));}
        return null;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

}