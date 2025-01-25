package spring.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.database.model.Director;

import java.util.UUID;

public interface DirectorRepository extends JpaRepository<Director, UUID> {
}