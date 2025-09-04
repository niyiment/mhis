package com.niyiment.mhis.mother.rule;

import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.validation.rule.BusinessRule;
import com.niyiment.mhis.validation.rule.ValidationResult;
import org.springframework.stereotype.Component;


/**
 * Validates obstetric history consistency
 */
@Component
public class ObstetricHistoryConsistencyRule implements BusinessRule<MotherCreateRequest> {

    @Override
    public boolean isApplicable(MotherCreateRequest data) {
        return data != null;
    }

    @Override
    public ValidationResult validate(MotherCreateRequest data) {
        Integer gravidity = data.gravidity() != null ? data.gravidity() : 0;
        Integer parity = data.parity() != null ? data.parity() : 0;
        Integer termBirths = data.termBirths() != null ? data.termBirths() : 0;
        Integer pretermBirths = data.pretermBirths() != null ? data.pretermBirths() : 0;
        Integer abortions = data.abortions() != null ? data.abortions() : 0;
        Integer livingChildren = data.livingChildren() != null ? data.livingChildren() : 0;

        if (gravidity != (parity + abortions)) {
            return ValidationResult.failure(
                    "GRAVIDITY_MISMATCH",
                    String.format("Gravidity (%d) should equal parity (%d) + abortions (%d)",
                            gravidity, parity, abortions),
                    "gravidity",
                    data.gravidity()
            );
        }

        if (parity != (termBirths + pretermBirths)) {
            return ValidationResult.failure(
                    "PARITY_MISMATCH",
                    String.format("Parity (%d) should equal term births (%d) + preterm births (%d)",
                            parity, termBirths, pretermBirths),
                    "parity",
                    data.parity()
            );
        }

        int totalBirths = termBirths + pretermBirths;
        if (livingChildren > totalBirths) {
            return ValidationResult.failure(
                    "LIVING_CHILDREN_EXCEED_BIRTHS",
                    String.format("Living children (%d) cannot exceed total birth (%d)",
                            livingChildren, totalBirths),
                    "livingChildren",
                    data.livingChildren()
            );
        }

        return ValidationResult.success();
    }

    @Override
    public String getRuleName() {
        return "ObstetricHistoryConsistencyRule";
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
