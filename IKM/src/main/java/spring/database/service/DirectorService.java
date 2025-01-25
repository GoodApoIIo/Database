package spring.database.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.database.model.Director;
import spring.database.repository.DirectorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository= directorRepository;
    }

    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public Director addDirector(Director director) {
        return directorRepository.save(director);
    }


    @Transactional
    public void updateDirector(UUID dorectorId, String newSurname, String newName, LocalDate newBirthDate, boolean newisAlive) {
        Director director = directorRepository.findById(dorectorId).orElseThrow();
        director.setSurname(newSurname);
        director.setName(newName);
        director.setBirthDate(newBirthDate);
        director.setAlive(newisAlive);
        directorRepository.save(director);
    }

    @Transactional
    public void deleteDirector(UUID directorId) {directorRepository.deleteById(directorId);}

    public Director getDirectorById(UUID id) {return directorRepository.findById(id).orElseThrow();}
}