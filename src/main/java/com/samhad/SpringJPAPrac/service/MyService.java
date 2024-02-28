package com.samhad.SpringJPAPrac.service;

import com.samhad.SpringJPAPrac.dao.PersonDao;
import com.samhad.SpringJPAPrac.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {

    @Autowired
    private PersonDao personDao;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
//        personDao.savePerson();
//        personDao.saveBulkRandomData();

        System.out.println("----------------------------------------\n\n");

//        List<Person> personList = personDao.findAll();
//        viewAllData(personList);

        List<Person> allWithCriteriaAndFetch = personDao.findAllWithCriteriaAndFetch();
        viewAllData(allWithCriteriaAndFetch);

//        List<Person> personListBasic = personDao.findAllWithCriteria();
//        viewAllPersons(personListBasic);
    }

    private void viewAllPersons(List<Person> personList) {
        personList.forEach(person -> System.out.println(person));
    }

    private void viewAllData(List<Person> personList) {
        try {
            personList.forEach(person -> {
                System.out.println(person);
                System.out.println(person.getEmployer());
                person.getAddresses().forEach(address -> {
                    System.out.println(address);
                    System.out.println(address.getAirport());
                });
                System.out.println("------------------------------------\n");
            });
        } catch (Exception e) {
            System.err.println("Exception caught in findAll");
            System.err.println(e);
        }
    }

}
