package project.guakamole.domain.applicant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.guakamole.domain.applicant.entity.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Long>, ApplicantSearchRepository {
}
