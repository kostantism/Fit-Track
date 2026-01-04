package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.service.PersonDataService;
import gr.hua.dit.fittrack.core.service.mapper.PersonMapper;
import gr.hua.dit.fittrack.core.service.model.PersonView;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonDataServiceImpl implements PersonDataService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonDataServiceImpl(PersonRepository personRepository,
                                 PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public List<PersonView> getAllPeople() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::convertPersonToPersonView)
                .toList();
    }

    //  ΝΕΟ
    @Override
    public List<PersonView> getAllTrainers() {
        return personRepository.findByType(PersonType.TRAINER)
                .stream()
                .map(personMapper::convertPersonToPersonView)
                .toList();
    }

    @Override
    public Person findPersonEntityById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Person not found with id " + id)
                );
    }

//    @Override
//    public Person findPersonEntityById(Long id) {
//        return personRepository.findById(id)
//                .orElseThrow(() ->
//                        new IllegalArgumentException("Person not found with id: " + id)
//                );
//    }

}

//package gr.hua.dit.fittrack.core.service.impl;
//
//import gr.hua.dit.fittrack.core.model.Person;
//import gr.hua.dit.fittrack.core.repository.PersonRepository;
//import gr.hua.dit.fittrack.core.service.PersonDataService;
//import gr.hua.dit.fittrack.core.service.mapper.PersonMapper;
//import gr.hua.dit.fittrack.core.service.model.PersonView;
//
//
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Default implementation of {@link PersonDataService}.
// */
//@Service
//public class PersonDataServiceImpl implements PersonDataService {
//
//    private final PersonRepository personRepository;
//    private final PersonMapper personMapper;
//
//    public PersonDataServiceImpl(final PersonRepository personRepository,
//                                 final PersonMapper personMapper) {
//        if (personRepository == null) throw new NullPointerException();
//        if (personMapper == null) throw new NullPointerException();
//        this.personRepository = personRepository;
//        this.personMapper = personMapper;
//    }
//
//    @Override
//    public List<PersonView> getAllPeople() {
//        final List<Person> personList = this.personRepository.findAll();
//        final List<PersonView> personViewList = personList
//                .stream()
//                .map(this.personMapper::convertPersonToPersonView)
//                .toList();
//        return personViewList;
//    }
//}
