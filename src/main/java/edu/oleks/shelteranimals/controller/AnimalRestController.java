package edu.oleks.shelteranimals.controller;

import edu.oleks.shelteranimals.model.Animal;
//import edu.oleks.shelteranimals.repository.AnimalRepository;
import edu.oleks.shelteranimals.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
@author   Oleksandra Hrytsiuk
@project  shelteranimals
@class  AnimalRestController
@version  4.0.0
@since 20.10.2024 - 13:55
*/
@RestController
@RequestMapping("api/v1/animals")
@RequiredArgsConstructor
public class AnimalRestController {
    private final AnimalService animalService;
    //private final AnimalRepository repository;


    @GetMapping("/")
    public List<Animal> getAllAnimals() {
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


