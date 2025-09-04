package com.niyiment.mhis.mother.rule;

import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.mother.repository.MotherRepository;
import com.niyiment.mhis.validation.rule.BusinessRule;
import com.niyiment.mhis.validation.rule.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validate that ANC Unique ID is not already in use
 */
@Component
@RequiredArgsConstructor
public class UniqueAncIdRule implements BusinessRule<MotherCreateRequest> {
    private final MotherRepository motherRepository;

    @Override
    public boolean isApplicable(MotherCreateRequest data) {
        return data != null && data.ancUniqueId() != null;
    }

    @Override
    public ValidationResult validate(MotherCreateRequest data) {
        if (motherRepository.existsByAncUniqueId(data.ancUniqueId())) {
            return ValidationResult.failure(
                    "DUPLICATE_ANC_ID",
                    "ANC Unique ID already exists: " + data.ancUniqueId(),
                    "ancUniqueId",
                    data.ancUniqueId()
            );
        }

        return ValidationResult.success();
    }

    @Override
    public String getRuleName() {
        return "UniqueAncIdRule";
    }

    @Override
    public int getPriority() {
        return 1;
    }


}
