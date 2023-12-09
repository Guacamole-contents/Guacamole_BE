package project.guakamole.domain.violation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.guakamole.domain.violation.entity.Violation;

public interface ViolationRepository extends JpaRepository<Violation, Long>, ViolationFilterRepository {
}
