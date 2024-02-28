package com.samhad.SpringJPAPrac.dao;

import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import com.samhad.SpringJPAPrac.entity.Address;
import com.samhad.SpringJPAPrac.entity.Airport;
import com.samhad.SpringJPAPrac.entity.Employer;
import com.samhad.SpringJPAPrac.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

@Transactional
@Repository
public class PersonDao {

    private static final Faker FAKER = Faker.instance();
    private static final RandomGenerator RANDOM = ThreadLocalRandom.current();

    @PersistenceContext
    private EntityManager entityManager;

    public void savePerson() {
        Address a1 = createAddress();
        Address a2 = createAddress();
        Person person = new Person(FAKER.name().name(), RANDOM.nextInt(20, 40), List.of(a1, a2));
        a1.setPerson(person);
        a2.setPerson(person);
        entityManager.persist(person);
        System.out.println("saved: " + person.getPId());
    }

    public void saveBulkRandomPersonWithAddress() {
        int max = 10;
        for (int i = 0; i < max; i++) {
            int maxAddresses = RANDOM.nextInt(1, 5);
            List<Address> addresses = new ArrayList<>(maxAddresses);
            Person person = new Person(FAKER.name().name(), RANDOM.nextInt(20, 40), addresses);
            for (int j = 0; j < maxAddresses; j++) {
                Address address = createAddress();
                address.setPerson(person);
                addresses.add(address);
            }

            entityManager.persist(person);
            System.out.println("saved: " + person.getPId());
        }
    }

    public void saveBulkRandomData() {
        int max = 10;
        for (int i = 0; i < max; i++) {
            int maxAddresses = RANDOM.nextInt(1, 5);
            List<Address> addresses = new ArrayList<>(maxAddresses);
            Company fakeCompany = FAKER.company();
            Person person = new Person(FAKER.name().name(), RANDOM.nextInt(20, 40), addresses);
            Employer employer = new Employer(fakeCompany.name(), fakeCompany.industry(), fakeCompany.profession(), person);
            Airport airport = new Airport(FAKER.aviation().airport());
            airport.setAddresses(addresses);
            person.setEmployer(employer);
            for (int j = 0; j < maxAddresses; j++) {
                Address address = createAddress();
                address.setPerson(person);
                if (RANDOM.nextBoolean()) {
                    address.setAirport(airport);
                }
                addresses.add(address);
            }

            entityManager.persist(person);
            System.out.println("saved: " + person.getPId());
        }
    }

    public List<Person> findAll() {
//        EntityGraph<?> employerGraph = entityManager.getEntityGraph("Person.employer");

//        List<String> hints = List.of("Person.employer", "Person.addresses");
//        List<EntityGraph<? super Person>> graphs = entityManager.getEntityGraphs(Person.class);

        // Adding multiple graphs not possible. Only one graph possible at a time.

        List<Person> resultList = entityManager
                .createQuery("select p from Person p " +
                        " left join fetch p.addresses ad " +
                        " left join fetch p.employer em " +
                        " left join fetch ad.airport ai ", Person.class)
//                .setHint("jakarta.persistence.fetchgraph", employerGraph)
                .getResultList();
        return resultList;
    }

    public List<Person> findAllWithCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Person> findAllWithCriteriaAndFetch() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);

        Root<Person> personRoot = criteriaQuery.from(Person.class);
        personRoot.fetch("employer", JoinType.LEFT);
        personRoot
                .fetch("addresses", JoinType.LEFT)
                .fetch("airport", JoinType.LEFT);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private Address createAddress() {
        return new Address(FAKER.address().streetName(), RANDOM.nextInt(780000, 789999));
    }

}
