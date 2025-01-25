package spring.database.controller;

import org.springframework.web.bind.annotation.*;
import spring.database.model.Genre;
import spring.database.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {this.genreService = genreService;}

    @GetMapping
    public List<Genre> getAllGenres() {return genreService.getAllGenres();}

    @PostMapping
    public Genre addGenre(@RequestBody Genre genre) {return genreService.addGenre(genre);}

    @PutMapping("/{id}")
    public void updateGenre(@PathVariable Integer id, @RequestBody Genre genre) {genreService.updateGenre(id, genre);}

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Integer id) {genreService.deleteGenre(id);}
}