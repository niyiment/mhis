package com.niyiment.mhis.validation.engine;


import com.niyiment.mhis.validation.rule.BusinessRule;
import com.niyiment.mhis.validation.rule.ValidationResult;
import com.niyiment.mhis.validation.rule.ValidationResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;


/**
 * Engine for executing business rules in order of priority
 */
@Slf4j
@Component
public class BusinessRuleEngine {

    public <T>ValidationResults execute(T data, List<BusinessRule<T>> rules, boolean stopOnFirstFailure) {
        ValidationResults results = new ValidationResults();
        if (data == null || rules == null || rules.isEmpty()) {
            log.warn("No data or rules provided for validation");
            return results;
        }

        List<BusinessRule<T>> sortedRules = rules.stream()
                .sorted(Comparator.comparingInt(BusinessRule::getPriority))
                .toList();

        log.debug("Executing {} business rules for data type: {}",
                sortedRules.size(), data.getClass().getSimpleName());

        for (BusinessRule<T> rule : sortedRules) {
            try {
                if (rule.isApplicable(data)) {
                    log.debug("Executing rule: {}", rule.getRuleName());
                    ValidationResult result = rule.validate(data);
                    results.add(result);

                    if (stopOnFirstFailure && result.isFailure()) {
                        log.debug("Stopping execution due to first failure in rule: {}", rule.getRuleName());
                        break;
                    }
                }
            } catch (Exception e) {
                log.error("Error executing business rule: {}", rule.getRuleName(), e);
                results.add(ValidationResult.failure(
                        "RULE_EXECUTION_ERROR",
                        "Error executing business rule: " + rule.getRuleName()
                ));
                if (stopOnFirstFailure) {
                    break;
                }
            }
        }
        log.debug("Validation completed, Valid: {}, Total results: {}",
                results.isValid(), results.size());
        return results;
    }

    // Execute all applicable business rules (does not stop on first failure
    public <T> ValidationResults execute(T data, List<BusinessRule<T>> rules) {
        return execute(data, rules, false);
    }

    // Execute rules and return true if all validation pass
    public <T> boolean isValid(T data, List<BusinessRule<T>> rules) {
        return execute(data, rules, true).isValid();
    }
}

