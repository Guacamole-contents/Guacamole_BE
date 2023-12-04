package project.guakamole.domain.applicant.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.guakamole.config.DataConfig;
import project.guakamole.domain.applicant.ApplicantObjectCreator;
import project.guakamole.domain.applicant.dto.response.FindApplicantResponse;
import project.guakamole.domain.applicant.entity.Applicant;
import project.guakamole.domain.applicant.entity.ApplicantApproveStatus;
import project.guakamole.domain.applicant.searchtype.ApplicantSearchType;
import project.guakamole.global.config.JpaAuditingConfig;

import static org.assertj.core.api.Assertions.assertThat;
@Import({DataConfig.class, JpaAuditingConfig.class})
@DataJpaTest
class ApplicantSearchRepositoryImplTest {

    @Autowired
    protected ApplicantRepository applicantRepository;
    @Autowired
    protected EntityManager entityManager;

    @BeforeEach
    public void beforeEach(){
        // cond 1
        String chanelName1 = "채널명1";
        String email1 = "test1@email.com";
        String chanelLink1 = "www.link1.com";
        ApplicantApproveStatus status1 = ApplicantApproveStatus.HOLD;

        // cond 2
        String chanelName2 = "채널명2";
        String email2 = "test2@email.com";
        String chanelLink2 = "www.link2.com";
        ApplicantApproveStatus status2 = ApplicantApproveStatus.APPROVE;


        // cond 1
        Applicant savedApplicant1 = applicantRepository.save(ApplicantObjectCreator.applicant(chanelName1, email1, email1, status1));
        Applicant savedApplicant2 = applicantRepository.save(ApplicantObjectCreator.applicant(chanelName1, email1, email1, status1));
        Applicant savedApplicant3 = applicantRepository.save(ApplicantObjectCreator.applicant(chanelName1, email1, email1, status1));

        // cond 2
        Applicant savedApplicant4 = applicantRepository.save(ApplicantObjectCreator.applicant(chanelName2, email2, email2, status2));
        Applicant savedApplicant5 = applicantRepository.save(ApplicantObjectCreator.applicant(chanelName2, email2, email2, status2));
    }
    @AfterEach
    public void teardown() {
        this.applicantRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE applicant ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
    }

    @Nested
    @DisplayName("조건 검색에 따른 신규 지원자 조회")
    class SearchApplicant{

        @Test
        @DisplayName("지원자 id로 검색")
        void searchApplicant_searchTypeEqId() {
            //given
            Pageable pageable = PageRequest.of(0, 10);
            ApplicantSearchType searchType = ApplicantSearchType.USER_ID;

            //when
            //cond 1
            Page<FindApplicantResponse> result1 = applicantRepository.searchApplicant(
                    searchType,
                    "1",
                    pageable
            );

            //cond 1 & cond 2
            Page<FindApplicantResponse> result2 = applicantRepository.searchApplicant(
                    searchType,
                    "2",
                    pageable
            );

            //then
            assertThat(result1.getContent().size()).isEqualTo(1);
            assertThat(result1.getContent().get(0).getId()).isEqualTo(1L);
            assertThat(result2.getContent().size()).isEqualTo(1);
            assertThat(result2.getContent().get(0).getId()).isEqualTo(2L);
        }

        @Test
        @DisplayName("채널명(저작권자 이름) 으로 검색")
        void searchApplicant_searchTypeEqChanelName() {
            //given
            Pageable pageable = PageRequest.of(0, 10);
            ApplicantSearchType searchType = ApplicantSearchType.CHANEL_NAME;

            //when
            //cond 1
            Page<FindApplicantResponse> result1 = applicantRepository.searchApplicant(
                    searchType,
                    "채널명1",
                    pageable
            );

            //cond 1 & cond 2
            Page<FindApplicantResponse> result2 = applicantRepository.searchApplicant(
                    searchType,
                    "채널명2",
                    pageable
            );

            //then
            assertThat(result1.getContent().size()).isEqualTo(3);
            assertThat(result1.getContent().get(0).getChanelName()).isEqualTo("채널명1");
            assertThat(result2.getContent().size()).isEqualTo(2);
            assertThat(result2.getContent().get(0).getChanelName()).isEqualTo("채널명2");
        }

        @Test
        @DisplayName("이메일로 검색")
        void searchApplicant_searchTypeEqEmail() {
            //given
            Pageable pageable = PageRequest.of(0, 10);
            ApplicantSearchType searchType = ApplicantSearchType.EMAIL;

            //when
            //cond 1
            Page<FindApplicantResponse> result1 = applicantRepository.searchApplicant(
                    searchType,
                    "test1@email.com",
                    pageable
            );

            //cond 1 & cond 2
            Page<FindApplicantResponse> result2 = applicantRepository.searchApplicant(
                    searchType,
                    "test",
                    pageable
            );

            //then
            assertThat(result1.getContent().size()).isEqualTo(3);
            assertThat(result1.getContent().get(0).getEmail()).isEqualTo("test1@email.com");
            assertThat(result2.getContent().size()).isEqualTo(5);
        }
    }

}