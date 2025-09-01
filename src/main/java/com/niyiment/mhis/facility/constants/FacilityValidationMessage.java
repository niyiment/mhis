package com.niyiment.mhis.facility.constants;

public final class FacilityValidationMessage {
    public static final String FACILITY_CODE_REQUIRED = "Facility code is required";
    public static final String FACILITY_CODE_MAX_LENGTH = "Facility code must not exceed 20 characters";
    public static final String FACILITY_CODE_PATTERN = "Facility code must contain only uppercase letters, numbers, hyphens, and underscores";

    public static final String FACILITY_NAME_REQUIRED = "Facility name is required";
    public static final String FACILITY_NAME_MAX_LENGTH = "Facility name must not exceed 255 characters";

    public static final String FACILITY_TYPE_REQUIRED = "Facility type is required";
    public static final String LEVEL_OF_CARE_REQUIRED = "Level of care is required";

    public static final String STATE_REQUIRED = "State is required";
    public static final String STATE_MAX_LENGTH = "State must not exceed 100 characters";

    public static final String LGA_REQUIRED = "Lga is required";
    public static final String LGA_MAX_LENGTH = "Lga must not exceed 100 characters";

    public static final String DHIS2_ID_MAX_LENGTH = "DHIS2 organization unit ID must not exceed 50 characters";

    public static final String CONTACT_PHONE_PATTERN = "Contact phone must be a valid phone number format";
    public static final String CONTACT_PHONE_MAX_LENGTH = "Contact phone must not exceed 20 characters";

}
