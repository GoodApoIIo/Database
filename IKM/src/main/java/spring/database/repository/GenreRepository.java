package spring.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.database.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}