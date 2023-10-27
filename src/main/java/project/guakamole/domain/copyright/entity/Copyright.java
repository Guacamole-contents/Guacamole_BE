package project.guakamole.domain.copyright.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Copyright {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "source_id", nullable = false)
    private Long sourceId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "link", nullable = false)
    private String link;
}
