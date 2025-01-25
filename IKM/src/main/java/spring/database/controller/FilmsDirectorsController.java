package spring.database.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.database.model.Director;
import spring.database.model.Film;
import spring.database.repository.DirectorRepository;
import spring.database.repository.FilmRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/film-directors")
public class FilmsDirectorsController {

    private final FilmRepository filmRepository;
    private final DirectorRepository directorRepository;

    public FilmsDirectorsController(FilmRepository filmRepository, DirectorRepository directorRepository) {
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
    }

    @GetMapping
    public String getAllFilmDirectors(Model model) {
        List<Film> films = filmRepository.findAll();
        List<Director> directors = directorRepository.findAll();
        model.addAttribute("films", films);
        model.addAttribute("directors", directors);
        return "film-directors";
    }

    @PostMapping("/add")
    public String addFilmDirector(@RequestParam UUID filmId, @RequestParam UUID directorId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new RuntimeException("Film not found"));
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new RuntimeException("Director not found"));
        film.getDirectors().add(director);
        filmRepository.save(film);
        return "redirect:/film-directors";
    }

    @PostMapping("/delete")
    public String deleteFilmDirector(@RequestParam UUID filmId, @RequestParam UUID directorId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new RuntimeException("Film not found"));
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new RuntimeException("Director not found"));
        film.getDirectors().remove(director);
        filmRepository.save(film);
        return "redirect:/film-directors";
    }
}