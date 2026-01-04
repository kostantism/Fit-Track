package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.service.NotificationService;
import gr.hua.dit.fittrack.core.service.PersonBusinessLogicService;
import gr.hua.dit.fittrack.core.service.mapper.PersonMapper;
import gr.hua.dit.fittrack.core.service.model.CreatePersonRequest;
import gr.hua.dit.fittrack.core.service.model.CreatePersonResult;
import gr.hua.dit.fittrack.core.service.model.PersonView;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonBusinessLogicServiceImpl implements PersonBusinessLogicService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    public PersonBusinessLogicServiceImpl(final PersonRepository personRepository,
                                          final PersonMapper personMapper,
                                          final PasswordEncoder passwordEncoder,
                                          NotificationService notificationService) {

        if (personRepository == null) throw new NullPointerException();
        if(personMapper == null) throw new NullPointerException();
        if (passwordEncoder == null) throw new NullPointerException();
        if(notificationService == null) throw new NullPointerException();

        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
    }



    @Override
    public CreatePersonResult createPerson(CreatePersonRequest createPersonRequest) {
        if(createPersonRequest == null) throw new NullPointerException();

        final PersonType type = createPersonRequest.type();
        final String firstName = createPersonRequest.firstName().strip();
        final String lastName = createPersonRequest.lastName().strip();
        final String emailAddress = createPersonRequest.emailAddress().strip();
        final String mobilePhoneNumber = createPersonRequest.mobilePhoneNumber().strip();
        final String rawPassword = createPersonRequest.rawPassword();

        //TODO email must be unique
        //TODO phone number must be unique

        final String hashedPassword = passwordEncoder.encode(rawPassword);


        Person person = new Person();
        person.setId(null); // auto generated
        person.setType(type);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmailAddress(emailAddress);
        person.setMobilePhoneNumber(mobilePhoneNumber);
        person.setPasswordHash(hashedPassword);
        person.setCreatedAt(null); // auto generated

        if(this.personRepository.existsByEmailAddressIgnoreCase(emailAddress)){
            return CreatePersonResult.fail("Email address already exists!");
        }

        if(this.personRepository.existsByMobilePhoneNumber(mobilePhoneNumber)){
            return CreatePersonResult.fail("Mobile phone number address already exists!");
        }


        person = this.personRepository.save(person);

        notificationService.notifyUserRegistered(person);

        final PersonView personView = this.personMapper.convertPersonToPersonView(person);

        return CreatePersonResult.success(personView);
    }
}
