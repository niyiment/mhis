package com.niyiment.mhis.mother.rule;

import com.niyiment.mhis.mother.domain.ArtStatus;
import com.niyiment.mhis.mother.domain.HivStatus;
import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.validation.rule.BusinessRule;
import com.niyiment.mhis.validation.rule.ValidationResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/** Validates HIV/ART information consistency */
@Component
public class HivArtConsistencyRule implements BusinessRule<MotherCreateRequest> {
  @Override
  public boolean isApplicable(MotherCreateRequest data) {
    return data != null && data.hivStatus() != null;
  }

  @Override
  public ValidationResult validate(MotherCreateRequest data) {
    HivStatus hivStatus = data.hivStatus();
    ArtStatus artStatus = data.artStatus();

    if (hivStatus.equals(HivStatus.NEGATIVE)) {
      if (artStatus != null && !artStatus.equals(ArtStatus.NAIVE)) {
        return ValidationResult.failure(
            "HIV_NEGATIVE_WITH_ART",
            "HIV negative mother cannot have ART status other than NAIVE",
            "artStatus",
            artStatus);
      }
      if (data.currentArtRegimen() != null) {
        return ValidationResult.failure(
            "HIV_NEGATIVE_WITH_ART_REGIMEN",
            "HIV negative mother cannot have an ART regimen",
            "currentArtRegimen",
            data.currentArtRegimen());
      }
    }

    if (hivStatus.equals(HivStatus.POSITIVE)
        && artStatus.equals(ArtStatus.ON_ART)
        && !StringUtils.hasText(data.currentArtRegimen())) {
      return ValidationResult.failure(
          "MISSING_ART_REGIMENT",
          "Mother on ART must have a specified regimen",
          "currentArtRegimen",
          data.currentArtRegimen());
    }

    if (!hivStatus.equals(HivStatus.POSITIVE) && data.lastVlDate() != null) {
      return ValidationResult.failure(
          "VL_FOR_NON_POSITIVE",
          "Viral load can only be recorded for HIV positive mothers",
          "lastVlResult",
          data.lastVlResult());
    }

    return ValidationResult.success();
  }

  @Override
  public String getRuleName() {
    return "HivArtConsistencyRule";
  }

  @Override
  public int getPriority() {
    return 5;
  }
}
