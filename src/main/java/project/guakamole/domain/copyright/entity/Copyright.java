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
    @Column(name = "source_id", nullable = false)
    private Long id;
    @Column(name = "owner_name", nullable = false)
    private String ownerName;
    @Column(name = "copyright_name", nullable = false)
    private String copyrightName;
    @Column(name = "original_link", nullable = false)
    private String originalLink;

    @Builder
    private Copyright(String ownerName, String copyrightName, String originalLink) {
        this.ownerName = ownerName;
        this.copyrightName = copyrightName;
        this.originalLink = originalLink;
    }

    public static Copyright create(CreateCopyrightRequest request){
        return Copyright.builder()
                .ownerName(request.getOwnerName())
                .copyrightName(request.getCopyrightName())
                .originalLink(request.getOriginalLink())
                .build()
                ;
    }
}
