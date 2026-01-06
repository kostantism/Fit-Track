package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.service.model.PersonView;

import java.util.List;

public interface PersonDataService {

    List<PersonView> getAllPeople();

    List<PersonView> getAllTrainers();

    Person findPersonEntityById(Long id);
}


