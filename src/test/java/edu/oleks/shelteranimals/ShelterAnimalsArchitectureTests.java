package edu.oleks.shelteranimals;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.mapping.Document;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
//import org.springframework.data.annotation.Id;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
/*
@author   Oleksandra Hrytsiuk
@project  shelteranimals
@class  ShelterAnimalsArchitectureTests
@version  4.0.0
@since 20.10.2024 - 13:55
*/
@SpringBootTest
class ShelterAnimalsArchitectureTests {

    private JavaClasses applicationClasses;
    @BeforeEach
    void initialize() {
        applicationClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("edu.oleks.shelteranimals");
    }


    @Test
    void shouldFollowLayerArchitecture()  {
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")
                //
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                //
                .check(applicationClasses);

    }
    @Test
    void repositoriesShouldNotDependOnServices() {
        noClasses()
                .that().resideInAPackage("..repository..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..service..")
                .because("out of arch rules")
                .check(applicationClasses);
    }


    @Test
    void controllersShouldNotDependOnOtherControllers() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..controller..")
                .because("out of arch rules")
                .check(applicationClasses);

    }
    @Test
    void  controllerClassesShouldBeNamedXController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should()
                .haveSimpleNameEndingWith("Controller")
                .check(applicationClasses);
    }
    @Test
    void  repositoryClassesShouldBeNamedXRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .haveSimpleNameEndingWith("Repository")
                .check(applicationClasses);
    }
    @Test
    void  serviceClassesShouldBeNamedXService() {
        classes()
                .that().resideInAPackage("..service..")
                .should()
                .haveSimpleNameEndingWith("Service")
                .check(applicationClasses);
    }
    @Test
    void controllerClassesShouldBeAnnotatedWithRestController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should()
                .beAnnotatedWith(RestController.class)
                .check(applicationClasses);
    }
    @Test
    void controllerClassesShouldBeAnnotatedWithRequestMapping() {
        classes()
                .that().resideInAPackage("..controller..")
                .should()
                .beAnnotatedWith(RequestMapping.class)
                .check(applicationClasses);
    }
    @Test
    void serviceClassesShouldBeAnnotatedWithService() {
        classes()
                .that().resideInAPackage("..service..")
                .should()
                .beAnnotatedWith(Service.class)
                .check(applicationClasses);
    }

    @Test
    void repositoryClassesShouldBeAnnotatedWithRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .beAnnotatedWith(Repository.class)
                .check(applicationClasses);
    }
    @Test
    void repositoryShouldBeInterface() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .beInterfaces()
                .check(applicationClasses);
    }
    @Test
    void interfacesShouldNotBeAnnotatedWithRequestMapping() {
        noClasses()
                .that().areInterfaces()
                .should()
                .beAnnotatedWith(RequestMapping.class)
                .because("Interfaces should not be annotated with @RequestMapping.")
                .check(applicationClasses);
    }
    @Test
    void anyControllerFieldsShouldNotBeAnnotatedAutowired() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should()
                .beAnnotatedWith(Autowired.class)
                .check(applicationClasses);
    }
    @Test
    void anyServiceFieldsShouldNotBeAnnotatedAutowired() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should()
                .beAnnotatedWith(Autowired.class)
                .check(applicationClasses);
    }
    @Test
    void anyRepositoryFieldsShouldNotBeAnnotatedAutowired() {
        noClasses()
                .that().resideInAPackage("..repository..")
                .should()
                .beAnnotatedWith(Autowired.class)
                .check(applicationClasses);
    }
    @Test
    void modelFieldsShouldBePrivate() {
        fields()
                .that().areDeclaredInClassesThat()
                .resideInAPackage("..model..")
                .should().notBePublic()
                .because("To encapsulate data and protect the integrity of model classes.")
                .check(applicationClasses);

    }
    @Test
    void modelClassesShouldBeAnnotatedWithDocument() {
        classes()
                .that().resideInAPackage("..model..")
                .should()
                .beAnnotatedWith(Document.class)
                .check(applicationClasses);
    }
    @Test
    void allControllerMethodsShouldBeAnnotatedWithHttpMethod() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..") // Методи в контролерах
                .should().beAnnotatedWith(GetMapping.class)
                .orShould().beAnnotatedWith(PostMapping.class)
                .orShould().beAnnotatedWith(PutMapping.class)
                .orShould().beAnnotatedWith(DeleteMapping.class)
                .orShould().beAnnotatedWith(RequestMapping.class)
                .because("All controller methods should be annotated with at least one HTTP annotation.")
                .check(applicationClasses);
    }
    @Test
    void allControllerMethodsShouldFollowNamingConvention() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..") // Методи в контролерах
                .should().haveNameStartingWith("get")
                .orShould().haveNameStartingWith("create")
                .orShould().haveNameStartingWith("update")
                .orShould().haveNameStartingWith("delete")
                .because("All controller methods should follow naming conventions that indicate the HTTP method.")
                .check(applicationClasses);
    }

    @Test
    void modelClassesShouldHavePublicConstructors() {
        noClasses()
                .that().resideInAPackage("..model..")
                .should().haveOnlyPrivateConstructors()
                .because("All model classes should have public constructors")
                .check(applicationClasses);
    }


}
