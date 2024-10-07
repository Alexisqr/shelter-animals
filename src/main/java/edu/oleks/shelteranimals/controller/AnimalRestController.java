package edu.oleks.shelteranimals.controller;

import edu.oleks.shelteranimals.model.Animal;
import edu.oleks.shelteranimals.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/animals")
@RequiredArgsConstructor
public class AnimalRestController {
    private final AnimalService animalService;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello )))))))))))))))))";
    }

    @GetMapping("/")
    public List<Animal> GetAllAnimals() {
        return animalService.getAnimals();
    }

    @GetMapping("/{id}")
    public Animal getAnimalById(@PathVariable("id") String id) {
        return animalService.getAnimalById(id);
    }

    @PostMapping("/")
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

    @DeleteMapping("/{id}")
    public String deleteAnimalById(@PathVariable("id") String id) {
        return animalService.deleteAnimalById(id);
    }

    @PutMapping("/{id}")
    public Animal updateAnimal(@PathVariable("id") String id, @RequestBody Animal animalDetails) {
        return animalService.updateAnimal(id, animalDetails);
    }


}