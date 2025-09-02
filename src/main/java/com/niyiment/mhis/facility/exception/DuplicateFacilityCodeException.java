package com.niyiment.mhis.facility.exception;

public class DuplicateFacilityCodeException extends RuntimeException {
  public DuplicateFacilityCodeException(String facilityCode) {
    super(String.format("Facility with code %s already exists", facilityCode));
  }
}
