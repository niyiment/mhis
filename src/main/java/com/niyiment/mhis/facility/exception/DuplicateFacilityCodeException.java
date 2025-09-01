package com.niyiment.mhis.facility.exception;

import com.niyiment.mhis.facility.domain.model.FacilityCode;

public class DuplicateFacilityCodeException extends RuntimeException {
  public DuplicateFacilityCodeException(FacilityCode facilityCode) {
    super(String.format("Facility with code %s already exists", facilityCode.getValue()));
  }

  public DuplicateFacilityCodeException(String message) {
    super(message);
  }
}
