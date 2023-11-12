package project.guakamole.domain.copyright.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.guakamole.domain.copyright.entity.Copyright;

public interface CopyrightRepository extends JpaRepository<Copyright, Long> {

    @Query("SELECT c FROM Copyright c")
    Slice<Copyright> findCopyrights(Pageable pageable);
}
