package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.service.PersonDataService;
import gr.hua.dit.fittrack.core.service.model.PersonView;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@code Person} resource.
 */
@RestController
@RequestMapping(value = "/api/v1/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonResource {

    private final PersonDataService personDataService;

    public PersonResource(final PersonDataService  personDataService) {
        if (personDataService == null) throw new NullPointerException();
        this.personDataService = personDataService;
    }

    /**
     * Secured REST endpoint (JWT required)
     */
    @PreAuthorize("hasRole('USER') or hasRole('TRAINER')")
    @GetMapping
    public List<PersonView> getAll() {
        return personDataService.getAllPeople();
    }
}
