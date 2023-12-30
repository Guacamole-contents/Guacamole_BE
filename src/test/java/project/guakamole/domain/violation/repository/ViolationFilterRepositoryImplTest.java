package project.guakamole.domain.violation.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.guakamole.config.DataConfig;
import project.guakamole.domain.copyright.CopyrightObjectCreator;
import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.copyright.repository.CopyrightRepository;
import project.guakamole.domain.violation.ViolationObjectCreator;
import project.guakamole.domain.violation.dto.FilterViolationDto;
import project.guakamole.domain.violation.dto.response.FindViolationResponse;
import project.guakamole.domain.violation.entity.AgreementType;
import project.guakamole.domain.violation.entity.ViolateReactLevel;
import project.guakamole.domain.violation.entity.Violation;
import project.guakamole.domain.violation.searchtype.ViolationSearchType;
import project.guakamole.global.config.JpaAuditingConfig;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({DataConfig.class, JpaAuditingConfig.class})
@DataJpaTest
class ViolationFilterRepositoryImplTest {
    @Autowired
    protected ViolationRepository violationRepository;
    @Autowired
    protected CopyrightRepository copyrightRepository;

    @Autowired
    protected EntityManager entityManager;

    @BeforeEach
    public void beforeEach() {
        // cond 1
        String ownerName1 = "저작권 소유자명1";
        String copyrightName1 = "저작권명1";
        String originalLink1 = "저작권 링크1";

        // cond 2
        String ownerName2 = "저작권 소유자명2";
        String copyrightName2 = "저작권명2";
        String originalLink2 = "저작권 링크2";

        // cond 1
        Copyright savedCopyright1 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName1, copyrightName1, originalLink1));

        //cond 2
        Copyright savedCopyright2 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName2, copyrightName2, originalLink2));

        //cond1
        String violatorName = "침해자명";
        LocalDateTime violateDate = LocalDateTime.of(2022, Month.DECEMBER, 25, 0, 0, 0);
        Integer violateMoment = 30;
        String link = "www.link.com";
        ViolateReactLevel reactLevel = ViolateReactLevel.EXAMINE;
        AgreementType agreementType = AgreementType.HOLD;
        Long agreementAmount = 10L;


        //cond2
        String violatorName2 = "침해자명2";
        LocalDateTime violateDate2 = LocalDateTime.of(2022, Month.DECEMBER, 25, 0, 0, 0);
        Integer violateMoment2 = 300;
        String link2 = "www.link.com2";
        ViolateReactLevel reactLevel2 = ViolateReactLevel.REACT;
        AgreementType agreementType2 = AgreementType.AGREEMENT;
        Long agreementAmount2 = 1L;
        Violation savedViolation1 = violationRepository.save(ViolationObjectCreator.violation(
                savedCopyright1,
                violatorName,
                violateDate,
                violateMoment,
                link,
                reactLevel,
                agreementType,
                agreementAmount
        ));

        Violation savedViolation2 = violationRepository.save(ViolationObjectCreator.violation(
                savedCopyright2,
                violatorName,
                violateDate,
                violateMoment,
                link,
                reactLevel,
                agreementType,
                agreementAmount
        ));

        Violation savedViolation3 = violationRepository.save(ViolationObjectCreator.violation(
                savedCopyright1,
                violatorName2,
                violateDate2,
                violateMoment2,
                link2,
                reactLevel2,
                agreementType2,
                agreementAmount2
        ));

    }

    @AfterEach
    public void teardown() {
        this.violationRepository.deleteAll();
        this.copyrightRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE copyright ALTER COLUMN `source_id` RESTART WITH 1")
                .executeUpdate();
        this.entityManager
                .createNativeQuery("ALTER TABLE violation ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
    }

    @Nested
    @DisplayName("침해 데이터 검색 조회 및 필터링")
    class SearchViolationWithFilter {

        @Nested
        @DisplayName("필터 없이 침해 데이터 조회")
        class SearchViolationWithFilter_noFilter {
            @Test
            @DisplayName("필터링 없이 저작권자 이름으로 침해 데이터를 검색하면 검색 조건에 맞는 모든 데이터가 조회된다.")
            void searchViolationWithFilter_noFilter1() {
                //given
                FilterViolationDto filter = new FilterViolationDto(null, null, null, null, null, null);
                ViolationSearchType searchType = ViolationSearchType.OWNER_NAME;
                String keyword1 = "저작권 소유자명1";
                String keyword2 = "저작권 소유자명2";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);
                Page<FindViolationResponse> result2 = violationRepository.searchViolationWithFilter(searchType, keyword2, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
                assertThat(result1.getContent().get(0).getOwnerName()).isEqualTo("저작권 소유자명1");
                assertThat(result2.getContent().size()).isEqualTo(1);
                assertThat(result2.getContent().get(0).getOwnerName()).isEqualTo("저작권 소유자명2");
            }

            @Test
            @DisplayName("필터링 없이 SourceId로 침해 데이터 검색하면 검색 조건에 맞는 모든 침해 데이터가 조회된다.")
            void searchViolationWithFilter_noFilter2() {// cond 1
                //given
                FilterViolationDto filter = new FilterViolationDto(null, null, null, null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                String keyword2 = "2";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);
                Page<FindViolationResponse> result2 = violationRepository.searchViolationWithFilter(searchType, keyword2, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
                assertThat(result1.getContent().get(0).getOwnerName()).isEqualTo("저작권 소유자명1");
                assertThat(result2.getContent().size()).isEqualTo(1);
                assertThat(result2.getContent().get(0).getOwnerName()).isEqualTo("저작권 소유자명2");
            }
        }

        @Nested
        @DisplayName("날짜 필터링 테스트")
        class searchViolationWithDateFilter {
            @Test
            @DisplayName("startDate만 지정하면 이후 데이터가 모두 조회된다.")
            void searchViolationWithDateFilter_1() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0),
                        null, null, null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
            }

            @Test
            @DisplayName("startDate와 endDate가 모두 존재하면 그 사이의 값을 조회한다.")
            void searchViolationWithDateFilter_2() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0),
                        LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0),
                        null, null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(0);
            }

            @Test
            @DisplayName("startDate와 endDate가 사이의 데이터가 있으면 해당 데이터는 조회된다.")
            void searchViolationWithDateFilter_3() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0),
                        LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0, 0),
                        null, null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
            }

            @Test
            @DisplayName("endDate 만 지정하면 endDate 이전 데이터가 모두 조회된다.")
            void searchViolationWithDateFilter_4() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null,
                        LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0, 0),
                        null, null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
            }
        }

        @Nested
        @DisplayName("합의 유형 필터링 테스트")
        class SearchViolationWithAgreementType {

            @Test
            @DisplayName("합의 유형이 한 개일때는 해당 합의 유형을 가진 데이터만 조회된다.")
            void searchViolationWithAgreementType_1() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null,
                        //보류, 침해합의, 제거 요청, 법적대응
                        List.of(1),
                        null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(1);
                assertThat(result1.getContent().get(0).getAgreementType()).isEqualTo(AgreementType.HOLD.getValue());
            }

            @Test
            @DisplayName("합의 유형 필터에 해당하는 데이터가 없으면 데이터가 조회되지 않는다.")
            void searchViolationWithAgreementType_2() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null,
                        //보류, 침해합의, 제거 요청, 법적대응
                        List.of(4),
                        null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(0);
            }

            @Test
            @DisplayName("합의 유형이 여러개인 경우에는 해당하는 합의 유형을 가진 모든 데이터가 조회된다.")
            void searchViolationWithAgreementType_3() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null,
                        //보류, 침해합의, 제거 요청, 법적대응
                        List.of(1, 2),
                        null, null, null);
                ViolationSearchType searchType = ViolationSearchType.OWNER_NAME;
                String keyword1 = "저작권 소유자명1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
                assertThat(result1.getContent().get(0).getAgreementType()).isEqualTo(AgreementType.HOLD.getValue());
                assertThat(result1.getContent().get(1).getAgreementType()).isEqualTo(AgreementType.AGREEMENT.getValue());
            }
        }

        @Nested
        @DisplayName("대응 단계 필터 테스트")
        class SearchViolationWithReactLevel {
            @Test
            @DisplayName("선택한 대응 단계가 한 개일때는 해당 합의 유형을 가진 데이터만 조회된다.")
            void searchViolationWithReactLevel_1() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null,
                        //검토단계, 대응단계, 완료단계
                        List.of(1),
                        null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(1);
                assertThat(result1.getContent().get(0).getReactLevel()).isEqualTo(ViolateReactLevel.EXAMINE.getValue());
            }

            @Test
            @DisplayName("선택한 대응 단계가 여러 개면 선택한 합의 유형을 가진 모든 데이터가 조회된다.")
            void searchViolationWithReactLevel_2() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null,
                        //검토단계, 대응단계, 완료단계
                        List.of(1, 2),
                        null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
                assertThat(result1.getContent().get(0).getReactLevel()).isEqualTo(ViolateReactLevel.EXAMINE.getValue());
                assertThat(result1.getContent().get(1).getReactLevel()).isEqualTo(ViolateReactLevel.REACT.getValue());
            }

            @Test
            @DisplayName("선택한 대응 단계에 해당하는 데이터가 없으면 조회되지 않는다.")
            void searchViolationWithReactLevel_3() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null,
                        //검토단계, 대응단계, 완료단계
                        List.of(3, 4),
                        null, null, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("합의 금액 필터 테스트")
        class searchViolationWithAgreementAmount {

            @Test
            @DisplayName("최소 금액만 기입하면 최소 금액 이상인 데이터가 조회된다.")
            void searchViolationWithAgreementAmount_1() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null, null, null,
                        1L, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
            }

            @Test
            @DisplayName("최소 금액 미만인 데이터는 조회되지 않는다.")
            void searchViolationWithAgreementAmount_1_2() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null, null, null,
                        2L, null);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(1);
            }

            @Test
            @DisplayName("최대 금액만 기입하면 최대 금액 이하인 데이터가 조회된다.")
            void searchViolationWithAgreementAmount_2() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null, null, null,
                        null, 10L);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
            }

            @Test
            @DisplayName("기입한 최대 금액 초과인 데이터는 조회되지 않는다.")
            void searchViolationWithAgreementAmount_2_2() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null, null, null,
                        null, 9L);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(1);
            }

            @Test
            @DisplayName("최소 금액과 최대 금액 둘 다 기입하면 사이에 있는 데이터가 조회된다.")
            void searchViolationWithAgreementAmount_3() {
                //given
                FilterViolationDto filter = new FilterViolationDto(
                        null, null, null, null,
                        1L, 10L);
                ViolationSearchType searchType = ViolationSearchType.SOURCE_ID;
                String keyword1 = "1";
                Pageable pageable = PageRequest.of(0, 10);

                //when
                Page<FindViolationResponse> result1 = violationRepository.searchViolationWithFilter(searchType, keyword1, filter, pageable);

                //then
                assertThat(result1.getContent().size()).isEqualTo(2);
            }
        }
    }
}
