package spring.database.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.database.model.Film;
import spring.database.service.FilmService;
import spring.database.service.GenreService;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/films")
public class FilmController {
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;
    private final GenreService genreService;

    public FilmController(FilmService filmService, GenreService genreService) {
        this.filmService = filmService;
        this.genreService = genreService;
    }

    @GetMapping
    public String getFilmsPage(Model model) {
        model.addAttribute("films", filmService.getAllFilms());
        model.addAttribute("film", new Film());
        model.addAttribute("genres", genreService.getAllGenres());
        return "films";
    }

    @PostMapping
    public String addFilm(@Valid @ModelAttribute Film film, BindingResult bindingResult, Model model) {
        logger.info("Attempting to add a new film: {}", film);
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors occurred: {}", bindingResult.getAllErrors());
            model.addAttribute("genres", genreService.getAllGenres());
            return "films";
        }
        try {
            filmService.addFilm(film);
            logger.info("Film added successfully: {}", film);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database constraint violation: {}", e.getMessage());
            model.addAttribute("error", "Database error: " + Objects.requireNonNull(e.getRootCause()).getMessage()); // Используем getRootCause() для получения конкретной ошибки
            model.addAttribute("genres", genreService.getAllGenres());
            return "films";
        }
        return "redirect:/films";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable UUID id) {
        try {
            filmService.deleteFilm(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/edit/{id}")
    public String editFilmForm(@PathVariable UUID id, Model model) {
        Film film = filmService.getFilmById(id);
        model.addAttribute("film", film);
        model.addAttribute("genres", genreService.getAllGenres());
        return "edit-film";
    }

    @PostMapping("/edit/{id}")
    public String updateFilm(@PathVariable UUID id, @Valid @ModelAttribute Film film, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAllGenres());
            return "edit-film";
        }
        filmService.updateFilm(id, film.getTitle(), film.getDescription(), film.getGenreId(), film.getPublicationDate(), film.getPopularityScore(), film.getBudget());
        return "redirect:/films";
    }

    @PostMapping("/complex-operation")
    public ResponseEntity<Void> performComplexOperation() {
        filmService.complexFilmOperation();
        return ResponseEntity.ok().build();
    }
}