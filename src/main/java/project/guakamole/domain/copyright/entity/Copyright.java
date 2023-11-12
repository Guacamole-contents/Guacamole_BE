package project.guakamole.domain.copyright.entity;

import jakarta.persistence.*;
import lombok.*;
import project.guakamole.domain.copyright.dto.request.CreateCopyrightRequest;
import project.guakamole.global.base.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Copyright extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "source_id", nullable = false)
    private Long sourceId;
    @Column(name = "owner_name", nullable = false)
    private String ownerName;
    @Column(name = "copyright_name", nullable = false)
    private String copyrightName;
    @Column(name = "original_link", nullable = false)
    private String originalLink;

    @Builder
    private Copyright(Long sourceId, String ownerName, String copyrightName, String originalLink) {
        this.sourceId = sourceId;
        this.ownerName = ownerName;
        this.copyrightName = copyrightName;
        this.originalLink = originalLink;
    }

    public static Copyright create(CreateCopyrightRequest request){
        return Copyright.builder()
                .sourceId(request.getSourceId())
                .ownerName(request.getOwnerName())
                .copyrightName(request.getCopyrightName())
                .originalLink(request.getOriginalLink())
                .build()
                ;
    }
}
