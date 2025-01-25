package spring.database.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.database.model.Genre;
import spring.database.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {this.genreRepository = genreRepository;}

    public List<Genre> getAllGenres() {return genreRepository.findAll();}

    public Genre addGenre(Genre genre) {return genreRepository.save(genre);}

    @Transactional
    public void updateGenre(Integer genreId, Genre newGenre) {
        Genre genre = genreRepository.findById(genreId).orElseThrow();
        genre.setName(newGenre.getName());
        genreRepository.save(genre);
    }

    @Transactional
    public void deleteGenre(Integer genreId) {genreRepository.deleteById(genreId);}
}