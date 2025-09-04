package com.niyiment.mhis.validation.rule;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for multiple validation results
 */
@Data
public class ValidationResults {
    private final List<ValidationResult> results = new ArrayList<>();

    public void add(ValidationResult result) {
        if (result != null) {
            results.add(result);
        }
    }

    public boolean isValid() {
        return results.stream().allMatch(ValidationResult::isValid);
    }

    public List<ValidationResult> getFailures() {
        return results.stream()
                .filter(ValidationResult::isFailure)
                .toList();
    }

    public ValidationResult getFirstFailure() {
        return results.stream()
                .filter(ValidationResult::isFailure)
                .findFirst()
                .orElse(null);
    }

    public boolean hasFailure() {
        return !isValid();
    }

    public int size() {
        return results.size();
    }
}
