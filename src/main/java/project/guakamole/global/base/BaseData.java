package project.guakamole.global.base;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import project.guakamole.domain.copyright.dto.request.CreateCopyrightRequest;
import project.guakamole.domain.copyright.service.CopyrightService;

@Configuration
@Profile({"dev"})
@Transactional
public class BaseData {
    @Bean
    CommandLineRunner initData(
            CopyrightService copyrightService
    ) {
        return args -> {
            //저작권 데이터 20개 생성
            for(int i=1; i<=20; i++){
                CreateCopyrightRequest createCopyrightRequest = new CreateCopyrightRequest(
                        "테스트데이터",
                        "테스트저작물",
                        "www.222.link"
                );
                copyrightService.createCopyright(createCopyrightRequest);
            }
        };
    }
}
