package edu.oleks.shelteranimals;

import edu.oleks.shelteranimals.model.Animal;
import edu.oleks.shelteranimals.repository.AnimalRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

/*
@author   Oleksandra Hrytsiuk
@project  shelteranimals
@class  RepositoryTest
@version  4.0.0
@since 20.10.2024 - 13:55
*/

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
public class RepositoryTest {

    @Autowired
    AnimalRepository underTest;

    @BeforeAll
    void beforeAll() {}

    @BeforeEach
    void setUp() {
        Animal whiskers = new Animal("1", "Whiskers", 2, "M", "###test", "New York, NY", "cat", "https://example.com/images/whiskers.jpg", false, "1", true);
        Animal bella = new Animal("2", "Bella", 3, "F", "###test", "Los Angeles, CA", "dog", "https://example.com/images/bella.jpg", false, "2", false);
        Animal milo = new Animal("3", "Milo", 1, "M", "###test", "Chicago, IL", "cat", "https://example.com/images/milo.jpg", false, "1", true);
        underTest.saveAll(List.of(whiskers, bella, milo));
    }

    @AfterEach
    void tearDown() {
        List<Animal> animalsToDelete = underTest.findAll().stream()
                .filter(animal -> animal.getDescription().contains("###test"))
                .toList();
        underTest.deleteAll(animalsToDelete);
    }

    @AfterAll
    void afterAll() {}

    @Test
    void testSetShouldContains_3_Records_ToTest(){
        List<Animal> animalsToDelete = underTest.findAll().stream()
                .filter(animal -> animal.getDescription().contains("###test"))
                .toList();
        assertEquals(3,animalsToDelete.size());
    }

    @Test
    void shouldGiveIdForNewRecord() {
        // given
        Animal luna = new Animal("Luna", 4, "F","###test", "New York, NY", "cat", "https://example.com/images/whiskers.jpg", false, "1", true);
        // when
        underTest.save(luna);
        Animal animalFromDb = underTest.findAll().stream()
                .filter(animal -> animal.getName().equals("Luna"))
                .findFirst().orElse(null);
        // then
        assertFalse(animalFromDb.getId() == luna.getId());
        assertNotNull(animalFromDb);
        assertNotNull(animalFromDb.getId());
        assertFalse(animalFromDb.getId().isEmpty());
        assertEquals(24, animalFromDb.getId().length());
    }

    @Test
    void shouldNotGiveIdForNewRecord() {
        // given
        Animal oliver = new Animal("5", "Oliver", 5, "M", "###test", "Miami, FL", "cat", "https://example.com/images/oliver.jpg", false, "1", false);

        // when
        underTest.save(oliver);
        Animal animalFromDb = underTest.findAll().stream()
                .filter(animal -> animal.getName().equals("Oliver"))
                .findFirst().orElse(null);

        // then
        assertNotNull(animalFromDb);
        assertEquals(oliver.getId(), animalFromDb.getId());
        assertNotNull(animalFromDb.getId());
    }

    @Test
    void shouldUpdateRecord() {
        //given
        Animal bella2 = new Animal("2", "Bella 2", 4, "F", "###test", "Los Angeles, CA", "dog", "https://example.com/images/bella.jpg", false, "2", false);
        // when
        underTest.save(bella2);
        Animal animalFromDb = underTest.findAll().stream()
                .filter(animal -> animal.getId().equals("2"))
                .findFirst().orElse(null);
        // then
        assertNotNull(animalFromDb);
        assertEquals(bella2.getName(), animalFromDb.getName());
        assertEquals(bella2.getAge(), animalFromDb.getAge());

    }
    @Test
    void shouldNotUpdateFieldsInRecord() {
        //given
        Animal whiskers = Animal.builder()
                .id("1")
                .name("Whiskers")
                .description("###test")
                .build();
        // when
        underTest.save(whiskers);
        Animal animalFromDb = underTest.findAll().stream()
                .filter(animal -> animal.getId().equals("1"))
                .findFirst().orElse(null);
        // then
        //assertNull(animalFromDb.getAge());
        assertNull(animalFromDb.getSex());
        assertNull(animalFromDb.getLocation());
        assertNull(animalFromDb.getType());
        assertNull(animalFromDb.getPhotos());
        assertNull(animalFromDb.getShelterId());
        assertFalse(animalFromDb.isAvailableForAdoption());

        assertEquals(whiskers.getName(), animalFromDb.getName());
        assertEquals(whiskers.getDescription(), animalFromDb.getDescription());

    }

}