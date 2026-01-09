package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.CustomerProfile;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.service.CustomerProfileService;
import gr.hua.dit.fittrack.core.service.PersonDataService;
import gr.hua.dit.fittrack.core.service.ProgressService;
import gr.hua.dit.fittrack.core.service.model.CustomerOverviewView;
import gr.hua.dit.fittrack.core.service.model.ProgressEntryView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TrainerCustomerOverviewService {

    private final AppointmentService appointmentService;
    private final CustomerProfileService customerProfileService;
    private final ProgressService progressService;
    private final PersonDataService personDataService;

    public TrainerCustomerOverviewService(
            AppointmentService appointmentService,
            CustomerProfileService customerProfileService,
            ProgressService progressService,
            PersonDataService personDataService
    ) {
        this.appointmentService = appointmentService;
        this.customerProfileService = customerProfileService;
        this.progressService = progressService;
        this.personDataService = personDataService;
    }

    public CustomerOverviewView getCustomerOverview(
            Long trainerId,
            Long customerId
    ) {

        boolean allowed =
                appointmentService.getApprovedAppointmentsForTrainer(trainerId)
                        .stream()
                        .anyMatch(a -> a.getCustomer().getId().equals(customerId));

        if (!allowed) {
            throw new SecurityException("You are not allowed to view this customer");
        }

        Person customer =
                personDataService.findPersonEntityById(customerId);

        CustomerProfile profile =
                customerProfileService.getOrCreateProfile(customer);

        List<ProgressEntryView> progress =
                progressService.getProgressForCustomer(customerId);

        return new CustomerOverviewView(
                customer.getId(),
                customer.getFirstName() + " " + customer.getLastName(),
                profile.getGoal(),
                profile.getNotes(),
                progress
        );
    }
}
