package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.ProgressEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressEntryRepository extends JpaRepository<ProgressEntry, Long> {

    List<ProgressEntry> findByCustomerIdOrderByEntryDateDesc(Long customerId);
}
