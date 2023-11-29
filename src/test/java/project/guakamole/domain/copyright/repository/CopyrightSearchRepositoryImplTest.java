package project.guakamole.domain.copyright.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.guakamole.config.DataConfig;
import project.guakamole.domain.copyright.CopyrightObjectCreator;
import project.guakamole.domain.copyright.dto.response.FindCopyrightResponse;
import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.copyright.searchtype.CopyrightSearchType;
import project.guakamole.global.config.JpaAuditingConfig;


@Import({DataConfig.class, JpaAuditingConfig.class})
@DataJpaTest
class CopyrightSearchRepositoryImplTest {
    @Autowired
    protected CopyrightRepository copyrightRepository;

    @Nested
    @DisplayName("조건 검색에 따른 저작권 목록 조회")
    class FindCopyrightsBySearchCond{
        @DisplayName("저작권자 이름으로 데이터 검색")
        @Test
        void findCopyrightsBySearchCond_searchTypeEqOwnerName() {
            //given

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
            Copyright savedCopyright2 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName1, copyrightName1, originalLink1));
            Copyright savedCopyright3 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName1, copyrightName1, originalLink1));

            // cond 2
            Copyright savedCopyright4 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName2, copyrightName2, originalLink2));
            Copyright savedCopyright5 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName2, copyrightName2, originalLink2));

            Pageable pageable = PageRequest.of(0, 10);

            //when
            //cond 1
            Page<FindCopyrightResponse> findCopyrights1 = copyrightRepository.findCopyrightsWithSearchCond(
                    CopyrightSearchType.OWNER_NAME,
                    "저작권 소유자명1",
                    pageable
            );

            //cond 2
            Page<FindCopyrightResponse> findCopyrights2 = copyrightRepository.findCopyrightsWithSearchCond(
                    CopyrightSearchType.OWNER_NAME,
                    "저작권 소유자명2",
                    pageable
            );

            // cond1 & cond2
            Page<FindCopyrightResponse> findCopyrights3 = copyrightRepository.findCopyrightsWithSearchCond(
                    CopyrightSearchType.OWNER_NAME,
                    "저작권",
                    pageable
            );

            //then
            Assertions.assertThat(findCopyrights1.getContent().size()).isEqualTo(3);
            Assertions.assertThat(findCopyrights2.getContent().size()).isEqualTo(2);
            Assertions.assertThat(findCopyrights3.getContent().size()).isEqualTo(5);
        }

        @DisplayName("저작권명으로 데이터 검색")
        @Test
        void findCopyrightsBySearchCond_searchTypeEqCopyrightName() {
            //given

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
            Copyright savedCopyright2 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName1, copyrightName1, originalLink1));
            Copyright savedCopyright3 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName1, copyrightName1, originalLink1));

            // cond 2
            Copyright savedCopyright4 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName2, copyrightName2, originalLink2));
            Copyright savedCopyright5 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName2, copyrightName2, originalLink2));

            Pageable pageable = PageRequest.of(0, 10);

            //when
            //cond 1
            Page<FindCopyrightResponse> findCopyrights1 = copyrightRepository.findCopyrightsWithSearchCond(
                    CopyrightSearchType.COPYRIGHT_NAME,
                    "저작권명1",
                    pageable
            );

            //cond 2
            Page<FindCopyrightResponse> findCopyrights2 = copyrightRepository.findCopyrightsWithSearchCond(
                    CopyrightSearchType.COPYRIGHT_NAME,
                    "저작권명2",
                    pageable
            );

            // cond1 & cond2
            Page<FindCopyrightResponse> findCopyrights3 = copyrightRepository.findCopyrightsWithSearchCond(
                    CopyrightSearchType.COPYRIGHT_NAME,
                    "저작권",
                    pageable
            );

            //then
            Assertions.assertThat(findCopyrights1.getContent().size()).isEqualTo(3);
            Assertions.assertThat(findCopyrights2.getContent().size()).isEqualTo(2);
            Assertions.assertThat(findCopyrights3.getContent().size()).isEqualTo(5);
        }

        @DisplayName("저작권 번호로 데이터 검색")
        @Test
        void findCopyrightsBySearchCond_searchTypeEqSourceId() {
            //given

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

            // cond 2
            Copyright savedCopyright2 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName2, copyrightName2, originalLink2));

            Pageable pageable = PageRequest.of(0, 10);

            //when
            //cond 1
            Page<FindCopyrightResponse> findCopyrights1 = copyrightRepository.findCopyrightsWithSearchCond(
                    CopyrightSearchType.SOURCE_ID,
                    String.valueOf(1L),
                    pageable
            );

            //cond 2
            Page<FindCopyrightResponse> findCopyrights2 = copyrightRepository.findCopyrightsWithSearchCond(
                    CopyrightSearchType.SOURCE_ID,
                    String.valueOf(2L),
                    pageable
            );

            //then

            Assertions.assertThat(findCopyrights1.getContent().get(0).getOwnerName()).isEqualTo("저작권 소유자명1");
            Assertions.assertThat(findCopyrights1.getContent().get(0).getCopyrightName()).isEqualTo("저작권명1");
            Assertions.assertThat(findCopyrights1.getContent().get(0).getOriginalLink()).isEqualTo("저작권 링크1");

            Assertions.assertThat(findCopyrights2.getContent().get(0).getOwnerName()).isEqualTo("저작권 소유자명2");
            Assertions.assertThat(findCopyrights2.getContent().get(0).getCopyrightName()).isEqualTo("저작권명2");
            Assertions.assertThat(findCopyrights2.getContent().get(0).getOriginalLink()).isEqualTo("저작권 링크2");
        }

        @DisplayName("검색 유형 설정 없이 데이터 검색")
        @Test
        void findCopyrightsBySearchCond_all_search() {
            //given

            // cond 1
            String ownerName1 = "저작권 소유자명1";
            String copyrightName1 = "저작권명1";
            String originalLink1 = "저작권 링크1";

            // cond 2
            String ownerName2 = "저작권 소유자명2";
            String copyrightName2 = "저작권명2";
            String originalLink2 = "저작권 링크2";

            // cond 3
            String ownerName3 = "저작권 소유자명1111";
            String copyrightName3 = "저작권명1111";
            String originalLink3 = "저작권 링크1111";

            // cond 1
            Copyright savedCopyright1 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName1, copyrightName1, originalLink1));

            // cond 2
            Copyright savedCopyright2 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName2, copyrightName2, originalLink2));

            // cond 3
            Copyright savedCopyright3 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName3, copyrightName3, originalLink3));

            Pageable pageable = PageRequest.of(0, 10);

            //when
            //cond 1 - 전체 데이터가 나와야 한다.
            Page<FindCopyrightResponse> findCopyrights1 = copyrightRepository.findCopyrightsWithSearchCond(
                    null,
                    "저작권",
                    pageable
            );

            //cond 2 - sourceId가 1인 or 저작권명에 1이 들어가는 데이터 전부
            Page<FindCopyrightResponse> findCopyrights2 = copyrightRepository.findCopyrightsWithSearchCond(
                    null,
                    String.valueOf(1L),
                    pageable
            );

            //then
            Assertions.assertThat(findCopyrights1.getContent().size()).isEqualTo(3); // cond1, cond2, cond3
            Assertions.assertThat(findCopyrights2.getContent().size()).isEqualTo(2); // cond1, cond3

            Assertions.assertThat(findCopyrights2.getContent().get(0).getOwnerName()).isEqualTo("저작권 소유자명1111"); // cond1, cond3
            Assertions.assertThat(findCopyrights2.getContent().get(1).getOwnerName()).isEqualTo("저작권 소유자명1"); // cond1, cond3
        }

        @DisplayName("검색 없이 전체 조회")
        @Test
        void findCopyrightsBySearchCond_all() {
            //given

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

            // cond 2
            Copyright savedCopyright2 = copyrightRepository.save(CopyrightObjectCreator.copyright(ownerName2, copyrightName2, originalLink2));


            Pageable pageable = PageRequest.of(0, 10);

            //when
            //cond 1 - 전체 데이터가 나와야 한다.
            Page<FindCopyrightResponse> findCopyrights1 = copyrightRepository.findCopyrightsWithSearchCond(
                    null,
                    null,
                    pageable
            );

            //then
            Assertions.assertThat(findCopyrights1.getContent().size()).isEqualTo(2); // cond1, cond2, cond3
            Assertions.assertThat(findCopyrights1.getContent().get(0).getOwnerName()).isEqualTo("저작권 소유자명2"); // cond1, cond3
            Assertions.assertThat(findCopyrights1.getContent().get(1).getOwnerName()).isEqualTo("저작권 소유자명1"); // cond1, cond3
        }
    }
}