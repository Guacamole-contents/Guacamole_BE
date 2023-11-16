package project.guakamole.domain.violation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.guakamole.domain.violation.entity.Violation;

public interface ViolationRepository extends JpaRepository<Violation, Long> {

    @Query("SELECT v FROM Violation v")
    Page<Violation> findViolations(Pageable pageable);
}
