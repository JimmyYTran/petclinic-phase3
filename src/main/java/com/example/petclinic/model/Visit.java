package com.example.petclinic.model;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Visit")
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dateOfVisit;
    private String description;

    // associations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToMany(cascade =
            {CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(
            name = "visit_vet",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "vet_id"))
    private Set<Vet> vets = new HashSet<>();

    protected Visit() {

    }

    public Visit(Long id) {
        this.id = id;
    }

    public Visit(Date dateOfVisit, String description) {

        this.dateOfVisit = dateOfVisit;
        this.description = description;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(Date dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Update relationship between this Visit and a Pet
    public void addPet(Pet pet) {

        addPet(pet, true);
    }

    public void addPet(Pet pet, Boolean updateRelationship) {

        this.pet = pet;
        if (updateRelationship) {
            pet.addVisit(this, false);
        }
    }

    public void removePet(Pet pet) {

        removePet(pet, true);
    }

    public void removePet(Pet pet, Boolean updateRelationship) {

        this.pet = null;
        if (updateRelationship) {
            pet.removeVisit(this, false);
        }

    }

    // Update relationship between this Visit and Vet
    public void addVet(Vet vet) {

        addVet(vet, true);
    }

    public void addVet(Vet vet, Boolean updateRelationship) {

        this.vets.add(vet);
        if (updateRelationship) {
            vet.addVisit(this, false);
        }
    }

    public void removeVet(Vet vet) {

        removeVet(vet, true);
    }

    public void removeVet(Vet vet, Boolean updateRelationship) {

        this.vets.remove(vet);
        if(updateRelationship) {
            vet.removeVisit(this, false);
        }
    }

    public Pet getPet() {
        return pet;
    }

    public Set<Vet> getVets() {
        return this.vets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(id, visit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Visit{");
        sb.append("id=").append(id);
        sb.append(", dateOfVisit=").append(dateOfVisit);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static VisitBuilder builder() { return new VisitBuilder(); }

    public static final class VisitBuilder {
        private Visit visit;

        private VisitBuilder() {
            visit = new Visit();
        }

        public static VisitBuilder aVisit() {
            return new VisitBuilder();
        }

        public VisitBuilder withId(Long id) {
            visit.setId(id);
            return this;
        }

        public VisitBuilder withDateOfVisit(Date dateOfVisit) {
            visit.setDateOfVisit(dateOfVisit);
            return this;
        }

        public VisitBuilder withDescription(String description) {
            visit.setDescription(description);
            return this;
        }

        public VisitBuilder withPet(Pet pet) {
            visit.addPet(pet);
            return this;
        }

        public Visit build() {
            return visit;
        }
    }
}
