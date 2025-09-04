package com.niyiment.mhis.mother.validation.rule;

import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.validation.rule.BusinessRule;
import com.niyiment.mhis.validation.rule.ValidationResult;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Validates pregnancy date consistency
 */
@Component
public class PregnancyDataConsistencyRule implements BusinessRule<MotherCreateRequest> {
    @Override
    public boolean isApplicable(MotherCreateRequest data) {
        return data != null && (data.lmpDate() != null || data.eddDate() != null);
    }

    @Override
    public ValidationResult validate(MotherCreateRequest data) {
        LocalDate lmpDate = data.lmpDate();
        LocalDate eddDate = data.eddDate();
        LocalDate today = LocalDate.now();

        if (lmpDate != null && lmpDate.isAfter(today)) {
            return ValidationResult.failure(
                    "LMP_DATE_FUTURE",
                    "Last menstrual period cannot be in the future",
                    "lmpDate",
                    lmpDate
            );
        }

        // EdD should be in the future (allowing for some flexibility)
        if (eddDate != null && eddDate.isBefore(today.minusWeeks(2))) {
            return ValidationResult.failure(
                    "EDD_PAST_DATE",
                    "Estimated delivery date cannot be significantly in the past",
                    "eddDate",
                    eddDate
            );
        }

        // if both LMP and EDD are provided, EDD should be approximately 280 days after LMP
        if (lmpDate != null && eddDate != null) {
            LocalDate calculatedEdd = lmpDate.plusDays(280);
            long daysDifference = Math.abs(calculatedEdd.toEpochDay() - eddDate.toEpochDay());

            if (daysDifference > 14) {
                return ValidationResult.failure(
                  "EDD_LMP_MISMATCH",
                  String.format("EDD (%s) does not align with LMP (%s). ExpectedEDD: %s",
                          eddDate, lmpDate, calculatedEdd),
                        "eddDate",
                        eddDate
                );
            }
        }

        return ValidationResult.success();
    }

    @Override
    public String getRuleName() {
        return "PregnancyDataConsistencyRule";
    }

    @Override
    public int getPriority() {
        return 6;
    }
}
