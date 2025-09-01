package com.niyiment.mhis.facility.exception;

import com.niyiment.mhis.facility.domain.model.FacilityCode;
import com.niyiment.mhis.facility.domain.model.FacilityId;

public class FacilityNotFoundException extends RuntimeException {
  public FacilityNotFoundException(FacilityId facilityId) {
    super(String.format("Facility with ID %s not found", facilityId));
  }

  public FacilityNotFoundException(FacilityCode facilityCode) {
    super(String.format("Facility with code %s not found", facilityCode));
  }

  public FacilityNotFoundException(String message) {
    super(message);
  }
}
