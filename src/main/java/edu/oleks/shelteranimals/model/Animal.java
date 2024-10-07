package edu.oleks.shelteranimals.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Animal {
    private String id;
    private String name;
    private int age;
    private String sex;
    private String description;
    private String location;
    private String type;
    private String photos;
    private boolean isAvailableForAdoption;
    private String shelterId;
    private boolean sterilization;

    public Animal( String name, int age, String sex, String description, String location, String type, String photos, boolean isAvailableForAdoption, String shelterId, boolean sterilization) {

        this.name = name;
        this.age = age;
        this.sex = sex;
        this.description = description;
        this.location = location;
        this.type = type;
        this.photos = photos;
        this.isAvailableForAdoption = isAvailableForAdoption;
        this.shelterId = shelterId;
        this.sterilization = sterilization;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return getId().equals(animal.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
