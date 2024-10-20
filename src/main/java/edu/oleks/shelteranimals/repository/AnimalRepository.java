package edu.oleks.shelteranimals.repository;

/*
@author   Oleksandra Hrytsiuk
@project  shelteranimals
@class  AnimalRepository
@version  4.0.0
@since 20.10.2024 - 13:55
*/
import edu.oleks.shelteranimals.model.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends MongoRepository<Animal, String> {

}
