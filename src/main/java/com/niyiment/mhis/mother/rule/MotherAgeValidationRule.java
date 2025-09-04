package com.niyiment.mhis.mother.rule;

import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.validation.rule.BusinessRule;
import com.niyiment.mhis.validation.rule.ValidationResult;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

/**
 * Validates age-related business rule
 */
@Component
public class MotherAgeValidationRule implements BusinessRule<MotherCreateRequest> {
    private static final int MIN_MOTHER_AGE = 10;
    private static final int MAX_MOTHER_AGE = 55;

    @Override
    public boolean isApplicable(MotherCreateRequest data) {
        return data != null && data.dateOfBirth() != null;
    }

    @Override
    public ValidationResult validate(MotherCreateRequest data) {
        LocalDate today = LocalDate.now();
        int age = Period.between(data.dateOfBirth(), today).getYears();

        if (age < MIN_MOTHER_AGE) {
            return ValidationResult.failure(
              "AGE_TOO_YOUNG",
              String.format("Mother age (%d) is below minimum age (%d)", age, MIN_MOTHER_AGE),
              "dateOfBirth",
              data.dateOfBirth()
            );
        }

        if (age > MAX_MOTHER_AGE) {
            return ValidationResult.failure(
                    "AGE_TOO_OLD",
                    String.format("Mother age (%d) exceeds maximum age (%d)", age, MAX_MOTHER_AGE),
                    "dateOfBirth",
                    data.dateOfBirth()
            );
        }

        return ValidationResult.success();
    }

    @Override
    public String getRuleName() {
        return "MotherAgeValidationRule";
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
