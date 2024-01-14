package project.guakamole.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE")
    private UserRole userRole;

    @Column(name = "status") // 0: none, 1: creator, 2: applicant ....
    private Integer status;

    @Builder
    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.status = 0; //초기엔 none
    }

    public void updateStatusWithCreator(){
        this.status = 1;
    }

    public void updateStatusWithApplicant(){
        this.status = 2;
    }

    public void updateStatusWithNone(){
        this.status = 0;
    }
}
