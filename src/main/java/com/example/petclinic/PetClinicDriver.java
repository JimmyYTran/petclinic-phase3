package com.example.petclinic;

import com.example.petclinic.controller.OwnerController;
import com.example.petclinic.controller.PetController;
import com.example.petclinic.controller.VetController;
import com.example.petclinic.controller.VisitController;
import com.example.petclinic.model.*;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class PetClinicDriver implements ExitCodeGenerator {

    private static ConfigurableApplicationContext context;

    private static OwnerController ownerController;
    private static PetController petController;
    private static VisitController visitController;
    private static VetController vetController;

    public static void main(String[] args) {

        // We'll need a reference to the Spring IoC container (it's context).
        context = SpringApplication.run(PetClinicDriver.class, args);

        testApp();

        // part of exit code implementation
        // System.exit(SpringApplication.exit(context));
    }

    private static void testApp() {

        // We need references to each of the controllers in the context.
        // We use the context to retrieve managed beans by name.
        // The name of the bean is the type of bean (it's name) in camelcase, with the first letter lowercase (by default).
        ownerController = (OwnerController) context.getBean("ownerController");
        petController = (PetController) context.getBean("petController");
        visitController = (VisitController) context.getBean("visitController");
        vetController = (VetController) context.getBean("vetController");


        // ***** Owner *****

        // create our owners
        /*
        Owner owner1 = new Owner("Homer Simpson", "742 Evergreen Terrace", "Springfield", "9395550113");
        Owner owner2 = new Owner("Marge Simpson", "742 Evergreen Terrace", "Springfield", "9395550113");
        Owner owner3 = new Owner("Lisa Simpson", "742 Evergreen Terrace", "Springfield", "9395550113");
        Owner owner4 = new Owner("Bart Simpson", "742 Evergreen Terrace", "Springfield", "9395550113");
         */
        Owner owner1 = Owner.builder().withName("Homer Simpson").withAddress("742 Evergreen Terrace").withCity("SpringField").withPhoneNumber("9395550113").build();
        Owner owner2 = Owner.builder().withName("Marge Simpson").withAddress("742 Evergreen Terrace").withCity("SpringField").withPhoneNumber("9395550113").build();
        Owner owner3 = Owner.builder().withName("Lisa Simpson").withAddress("742 Evergreen Terrace").withCity("SpringField").withPhoneNumber("9395550113").build();
        Owner owner4 = Owner.builder().withName("Bart Simpson").withAddress("742 Evergreen Terrace").withCity("SpringField").withPhoneNumber("9395550113").build();

        // save owners to database
        ownerController.add(owner1);
        ownerController.add(owner2);
        ownerController.add(owner3);
        ownerController.add(owner4);

        // get all owners from database and display them
        display(ownerController.getAll());

        // ***** Pet *****

        // create some pets and add them to an existing owner
        /*
        Pet pet1 = new Pet("Godzilla", new Date(), PetType.LIZARD);
        Pet pet2 = new Pet("Santa's Little Helper", new Date(), PetType.DOG);
         */
        Pet pet1 = Pet.builder().withName("Godzilla").withBirthDate(new Date(2000, 12, 1)).withPetType(PetType.LIZARD).build();
        Pet pet2 = Pet.builder().withName("Santa's Little Helper").withBirthDate(new Date(2003, 12, 25)).withPetType(PetType.DOG).build();
        owner4.addPet(pet1);
        owner4.addPet(pet2);

        // display the owner info again
        display(ownerController.getAll());

        /*
        Pet pet3 = new Pet("Strangles", new Date(), PetType.SNAKE);
        Pet pet4 = new Pet("Stompy", new Date(), PetType.ELEPHANT);
         */
        Pet pet3 = Pet.builder().withName("Strangles").withBirthDate(new Date(2010, 3, 24)).withPetType(PetType.SNAKE).build();
        Pet pet4 = Pet.builder().withName("Stompy").withBirthDate(new Date(1999, 5, 13)).withPetType(PetType.ELEPHANT).build();

        petController.add(pet1);
        petController.add(pet2);
        petController.add(pet3);
        petController.add(pet4);

        display(petController.getAll());

        // ***** Visit *****
        /*
        Visit visit1 = new Visit(new Date(), "description");
        Visit visit2 = new Visit(new Date(), "description");
         */
        Visit visit1 = Visit.builder().withDateOfVisit(new Date(2016, 7, 29)).withDescription("Good visit!").withPet(pet2).build();
        Visit visit2 = Visit.builder().withDateOfVisit(new Date(2016, 8, 21)).withDescription("Okay visit").withPet(pet3).build();

        visitController.add(visit1);
        visitController.add(visit2);

        petController.modify(pet2);
        petController.modify(pet3);

        display(visitController.getAll());

        // ***** Vet *****
        List<Speciality> specialities = new ArrayList<Speciality>() {{
            add(Speciality.DENTISTRY);
            add(Speciality.RADIOLOGY);
        }};

        List<Visit> visits = new ArrayList<Visit>() {{
            add(visit1);
            add(visit2);
        }};

        /*
        Vet vet1 = new Vet("Veterinarian", specialities, visits);
         */
        Vet vet1 = Vet.builder().withName("Veter").withSpecialities(specialities).withVisits(visits).build();

        vetController.add(vet1);

        visitController.modify(visit1);
        visitController.modify(visit2);

        display(vetController.getAll());


    }

    private static void display(Object obj) {

        if (obj instanceof List) {

            List<?> list = (List) obj;
            for (Object o : list) {
                System.out.println("\t" + o);
            }

        } else {

            System.out.println(obj);

        }

        System.out.println();
    }

    @Override
    public int getExitCode() {
        return 42;
    }
}
