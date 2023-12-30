package project.guakamole.domain.copyright.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.guakamole.domain.copyright.entity.Copyright;

public interface CopyrightRepository extends JpaRepository<Copyright, Long>, CopyrightSearchRepository {
}
