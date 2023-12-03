package project.guakamole.domain.violation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.guakamole.domain.violation.entity.Violation;

public interface ViolationRepository extends JpaRepository<Violation, Long>, ViolationSearchRepository {

    @Query("SELECT v FROM Violation v " +
            "JOIN FETCH v.copyright")
    Page<Violation> findViolationWithFilter(Pageable pageable);
}
