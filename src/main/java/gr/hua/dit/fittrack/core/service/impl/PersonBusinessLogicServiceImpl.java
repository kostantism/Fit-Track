package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.service.PersonBusinessLogicService;
import gr.hua.dit.fittrack.core.service.model.CreatePersonRequest;
import gr.hua.dit.fittrack.core.service.model.CreatePersonResult;

public class PersonBusinessLogicServiceImpl implements PersonBusinessLogicService {

    private PersonRepository personRepository;

    public PersonBusinessLogicServiceImpl(PersonRepository personRepository) {

        if (personRepository == null) throw new NullPointerException();

        this.personRepository = personRepository;
    }



    @Override
    public CreatePersonResult createPerson(CreatePersonRequest createPersonRequest) {
        return null;
    }
}
