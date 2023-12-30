package project.guakamole.domain.applicant.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicantTest {

    @Test
    @DisplayName("Applicant 생성시 List 생성되는지 테스트")
    void t01(){
        //given, when
        Applicant applicant = Applicant.builder()
                .email("test@email.com")
                .chanelLink("www.test.com")
                .approveStatus(ApplicantApproveStatus.HOLD)
                .creatorName("테스트 채널명")
                .build();

        //then
        Assertions.assertThat(applicant.getImages()).isNotNull();
    }

    @Test
    @DisplayName("Applicant 생성시 note 가 null이 아닌지 테스트")
    void t02(){
        //given, when
        Applicant applicant = Applicant.builder()
                .email("test@email.com")
                .chanelLink("www.test.com")
                .approveStatus(ApplicantApproveStatus.HOLD)
                .creatorName("테스트 채널명")
                .build();

        //then
        Assertions.assertThat(applicant.getNote()).isNotNull();
    }
}