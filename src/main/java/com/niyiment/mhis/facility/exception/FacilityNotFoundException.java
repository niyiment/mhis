package com.niyiment.mhis.facility.exception;

import java.util.UUID;

public class FacilityNotFoundException extends RuntimeException {
  public FacilityNotFoundException(UUID facilityId) {
    super(String.format("Facility with ID %s not found", facilityId.toString()));
  }

  public FacilityNotFoundException(String facilityCode) {
    super(String.format("Facility with code %s not found", facilityCode));
  }

}
