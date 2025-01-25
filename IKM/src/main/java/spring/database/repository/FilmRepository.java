package spring.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.database.model.Film;

import java.util.UUID;

public interface FilmRepository extends JpaRepository<Film, UUID> {
}