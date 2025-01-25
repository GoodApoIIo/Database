package spring.database.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import spring.database.model.Film;
import spring.database.repository.FilmRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FilmService {

    private static final Logger logger = LoggerFactory.getLogger(FilmService.class);

    private final FilmRepository filmRepository;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public FilmService(FilmRepository filmRepository, TransactionTemplate transactionTemplate) {
        this.filmRepository = filmRepository;
        this.transactionTemplate = transactionTemplate;
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @Transactional
    public Film addFilm(Film film) {
        try {
            return filmRepository.save(film);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Invalid data: " + e.getMessage());
        }
    }

    @Transactional
    public void updateFilm(UUID filmId, String newTitle, String newDescription, Integer newGenreId, LocalDate newPublicationDate, float newPopularityScore, int newBudget) {
        Film film = filmRepository.findById(filmId).orElseThrow();
        film.setTitle(newTitle);
        film.setDescription(newDescription);
        film.setGenreId(newGenreId);
        film.setPublicationDate(newPublicationDate);
        film.setPopularityScore(newPopularityScore);
        film.setBudget(newBudget);
        filmRepository.save(film);
    }

    @Transactional
    public void deleteFilm(UUID filmId) {filmRepository.deleteById(filmId);}

    public Film getFilmById(UUID id) {return filmRepository.findById(id).orElseThrow();}

    public void complexFilmOperation() {
        logger.info("Starting complex film operation...");
        transactionTemplate.execute(status -> {
            logger.info("Savepoint created.");
            status.setRollbackOnly();
            logger.info("Rolled back to savepoint.");
            return null;
        });
        transactionTemplate.execute(status -> {
            logger.info("New transaction started.");
            Film film1 = new Film();
            film1.setTitle("New Film 1");
            film1.setDescription("Description of New Film 1");
            film1.setGenreId(1);
            film1.setPublicationDate(LocalDate.now());
            film1.setPopularityScore(5.0f);
            film1.setBudget(15000000);
            filmRepository.save(film1);
            logger.info("Created and saved new film 1.");
            filmRepository.flush();
            logger.info("Flushed the table.");
            Film film2 = new Film();
            film2.setTitle("New Film 2");
            film2.setDescription("Description of New Book 2");
            film2.setGenreId(2);
            film2.setPublicationDate(LocalDate.now());
            film2.setPopularityScore(4.5f);
            film2.setBudget(20000000);
            filmRepository.save(film2);
            logger.info("Created and saved new film 2.");
            status.setRollbackOnly();
            logger.info("Transaction marked for rollback.");
            return null;
        });
        logger.info("Complex film operation completed.");
    }
}