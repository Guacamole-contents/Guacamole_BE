package project.guakamole.domain.creator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.guakamole.domain.creator.entity.Creator;

public interface CreatorRepository extends JpaRepository<Creator, Long>, CreatorSearchRepository {
}
