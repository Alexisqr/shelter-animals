package edu.oleks.shelteranimals.service;

import edu.oleks.shelteranimals.model.Animal;
import edu.oleks.shelteranimals.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    private List<Animal> animals = new ArrayList<Animal>();
    {
        animals.add(new Animal("1","name1",1,"M","description1","location","cat","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww",false,"1",true));
        animals.add(new Animal("2","name2",1,"F","description2","location2","dog","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww",false,"2",false));
        animals.add(new Animal("3","name3",1,"M","description3","location3","cat","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww",false,"1",true));
    }

    // @PostConstruct
    void init() {
        animalRepository.deleteAll();
        animalRepository.saveAll(animals);
    }

    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }
    public Animal getAnimalById(String id) {
        return animalRepository.findById(id).orElse(null);
    }
    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }
    public String deleteAnimalById(String id) {
        animalRepository.deleteById(id);
        return id;
    }
    public Animal updateAnimal(String id, Animal animalDetails) {
        Animal animal = animalRepository.findById(id).orElse(null);

        if (animal != null) {
            animal.setName(animalDetails.getName());
            animal.setAge(animalDetails.getAge());
            animal.setSex(animalDetails.getSex());
            animal.setDescription(animalDetails.getDescription());
            animal.setLocation(animalDetails.getLocation());
            animal.setType(animalDetails.getType());
            animal.setPhotos(animalDetails.getPhotos());
            animal.setAvailableForAdoption(animalDetails.isAvailableForAdoption());
            animal.setShelterId(animalDetails.getShelterId());
            animal.setSterilization(animalDetails.isSterilization());

            return animalRepository.save(animal);
        } else {
            return null;
        }
    }

}
