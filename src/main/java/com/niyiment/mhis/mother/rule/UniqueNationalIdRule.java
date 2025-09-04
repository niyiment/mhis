package com.niyiment.mhis.mother.rule;

import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.mother.repository.MotherRepository;
import com.niyiment.mhis.validation.rule.BusinessRule;
import com.niyiment.mhis.validation.rule.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validates that the provided national ID is unique
 */
@Component
@RequiredArgsConstructor
public class UniqueNationalIdRule implements BusinessRule<MotherCreateRequest> {
    private final MotherRepository motherRepository;

    @Override
    public boolean isApplicable(MotherCreateRequest data) {
        return data != null && StringUtils.hasText(data.nationalId());
    }

    @Override
    public ValidationResult validate(MotherCreateRequest data) {
        if (motherRepository.existsByNationalId(data.nationalId())) {
            return ValidationResult.failure(
                    "DUPLICATE_NATIONAL_ID",
                    "National ID already exists: " + data.nationalId(),
                    "nationalId",
                    data.nationalId()
            );
        }

        return ValidationResult.success();
    }

    @Override
    public String getRuleName() {
        return "UniqueNationalIdRule";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
