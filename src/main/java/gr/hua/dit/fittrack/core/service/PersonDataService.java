//package gr.hua.dit.fittrack.core.service;
//
//import gr.hua.dit.fittrack.core.model.Person;
//import gr.hua.dit.fittrack.core.service.model.PersonView;
//
//import java.util.List;
//
//public interface PersonDataService {
//
//    List<PersonView> getAllPeople();
//
//    List<PersonView> getAllTrainers();
//
//    // ✅ ΝΕΟ — για UI controllers
//    Person findPersonEntityById(Long id);
//}

package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.service.model.PersonView;
import java.util.List;
import gr.hua.dit.fittrack.core.model.Person;

public interface PersonDataService {

    List<PersonView> getAllPeople();

    //  ΠΡΕΠΕΙ ΝΑ ΥΠΑΡΧΕΙ
    List<PersonView> getAllTrainers();

    Person findPersonEntityById(Long id);
}

//package gr.hua.dit.fittrack.core.service;
//
//import gr.hua.dit.fittrack.core.service.model.PersonView;
//
//import java.util.List;
//
//public interface PersonDataService {
//    List<PersonView> getAllPeople();
//}
