package project.guakamole.domain.violation;

import project.guakamole.domain.copyright.entity.Copyright;
import project.guakamole.domain.violation.entity.AgreementType;
import project.guakamole.domain.violation.entity.ViolateReactLevel;
import project.guakamole.domain.violation.entity.Violation;

import java.time.LocalDateTime;

public class ViolationObjectCreator {

    public static Violation violation(
            Copyright copyright,
            String violatorName,
            LocalDateTime violateDate,
            Integer violateMoment,
            String link,
            ViolateReactLevel reactLevel,
            AgreementType agreementType,
            Long agreementAmount)
    {
        return Violation.builder().
                copyright(copyright)
                .violatorName(violatorName)
                .violateDate(violateDate)
                .violateMoment(violateMoment)
                .violateLink(link)
                .reactLevel(reactLevel) //초기 검토중
                .agreementType(agreementType)
                .agreementAmount(agreementAmount)
                .build();
    }



}
