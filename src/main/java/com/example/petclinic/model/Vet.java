package com.example.petclinic.model;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Vet")
@Table(name = "vet")
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ElementCollection(targetClass = Speciality.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "vet_specialities")
    @Column(name = "speciality")
    private List<Speciality> specialities;

    // associations
    @ManyToMany(
            mappedBy = "vets",
            fetch = FetchType.LAZY)
    private Set<Visit> visits = new HashSet<>();

    protected Vet() {

    }

    public Vet(Long id) {

        this.id = id;
    }

    public Vet(String name, List<Speciality> specialities, Set<Visit> visits) {

        this.name = name;
        this.specialities = specialities;
        this.visits = visits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    // Update relationship between this Vet and visits
    public void addVisit(Visit visit) {

        addVisit(visit, true);
    }

    public void addVisit(Visit visit, Boolean updateRelationship) {

        this.visits.add(visit);
        if(updateRelationship) {
            visit.addVet(this, false);
        }
    }

    public void removeVisit(Visit visit) {
        removeVisit(visit, true);
    }

    public void removeVisit(Visit visit, Boolean updateRelationship) {

        this.visits.remove(visit);
        if(updateRelationship) {
            visit.removeVet(this, false);
        }
    }

    public Set<Visit> getVisits() {

        return this.visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vet vet = (Vet) o;
        return Objects.equals(id, vet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vet{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static VetBuilder builder() { return new VetBuilder(); }

    public static final class VetBuilder {
        private Vet vet;

        private VetBuilder() {
            vet = new Vet();
        }

        public static VetBuilder aVet() {
            return new VetBuilder();
        }

        public VetBuilder withId(Long id) {
            vet.setId(id);
            return this;
        }

        public VetBuilder withName(String name) {
            vet.setName(name);
            return this;
        }

        public VetBuilder withSpecialities(List<Speciality> specialities) {
            vet.setSpecialities(specialities);
            return this;
        }

        public VetBuilder withVisits(List<Visit> visits) {
            visits.stream().forEach(T -> vet.addVisit(T));
            return this;
        }

        public Vet build() {
            return vet;
        }
    }
}
