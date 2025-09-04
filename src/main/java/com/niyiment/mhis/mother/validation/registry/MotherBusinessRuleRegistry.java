package com.niyiment.mhis.mother.validation.registry;


import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.mother.validation.rule.*;
import com.niyiment.mhis.validation.rule.BusinessRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Registry for Mother related business rules
 */
@Component
@RequiredArgsConstructor
public class MotherBusinessRuleRegistry {
    private final UniqueAncIdRule uniqueAncIdRule;
    private final UniqueNationalIdRule uniqueNationalIdRule;
    private final ValidFacilityRule validFacilityRule;
    private final MotherAgeValidationRule motherAgeValidationRule;
    private final ObstetricHistoryConsistencyRule obstetricHistoryConsistencyRule;
    private final HivArtConsistencyRule hivArtConsistencyRule;
    private final PregnancyDataConsistencyRule pregnancyDataConsistencyRule;

    public List<BusinessRule<MotherCreateRequest>> getCreateRules() {
        return List.of(
                uniqueAncIdRule, uniqueNationalIdRule,
                validFacilityRule, motherAgeValidationRule,
                obstetricHistoryConsistencyRule, hivArtConsistencyRule,
                pregnancyDataConsistencyRule
        );
    }

    public List<BusinessRule<MotherCreateRequest>> getCriticalCreationRule() {
        return List.of(
          uniqueAncIdRule,
          uniqueNationalIdRule,
          validFacilityRule,
          motherAgeValidationRule
        );
    }

    public List<BusinessRule<MotherCreateRequest>> getConsistencyRules() {
        return List.of(
                motherAgeValidationRule,
                obstetricHistoryConsistencyRule,
                hivArtConsistencyRule,
                pregnancyDataConsistencyRule
        );
    }
}

