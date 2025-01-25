package spring.database.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.database.model.Director;
import spring.database.service.DirectorService;

import java.util.UUID;

@Controller
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public String getDirectorPage(Model model) {
        model.addAttribute("directors", directorService.getAllDirectors());
        model.addAttribute("director", new Director());
        return "directors";
    }

    @PostMapping
    public String addDirector(@Valid @ModelAttribute Director director, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("directors", directorService.getAllDirectors());
            return "directors";
        }
        directorService.addDirector(director);
        return "redirect:/directors";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable UUID id) {
        try {
            directorService.deleteDirector(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/edit/{id}")
    public String editDirectorForm(@PathVariable UUID id, Model model) {
        Director director = directorService.getDirectorById(id);
        model.addAttribute("director", director);
        return "edit-director";
    }

    @PostMapping("/edit/{id}")
    public String updateDirector(@PathVariable UUID id, @Valid @ModelAttribute Director director, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {return "edit-director";}
        directorService.updateDirector(id, director.getSurname(), director.getName(), director.getBirthDate(), director.isAlive());
        return "redirect:/directors";
    }
}