package project.guakamole.domain.violation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.guakamole.domain.copyright.entity.Copyright;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copyright_id")
    private Copyright copyright;

    @Column(name = "violator_name", nullable = false)
    private String violatorName;

    @Column(name = "violate_date", nullable = false)
    private LocalDateTime violateDate;

    @Column(name = "violate_react_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private ViolateReactLevel reactLevel;
}
