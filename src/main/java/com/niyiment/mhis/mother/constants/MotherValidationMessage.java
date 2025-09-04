package com.niyiment.mhis.mother.constants;

public final class MotherValidationMessage {
    public static final String FIRST_NAME_REQUIRED = "First name is required";
    public static final String FIRST_NAME_MAX_LENGTH = "First name must not exceed 100 characters";
    public static final String MIDDLE_NAME_MAX_LENGTH = "Middle name must not exceed 100 characters";
    public static final String LAST_NAME_REQUIRED = "Last name is required";
    public static final String LAST_NAME_MAX_LENGTH = "Last name must not exceed 100 characters";
    public static final String DATE_OF_BIRTH_REQUIRED = "Date of birth is required";
    public static final String DATE_OF_BIRTH_PAST = "Date of birth must be in the past";
    public static final String FACILITY_REQUIRED = "Facility is required";
    public static final String ANC_UNIQUE_ID_REQUIRED = "ANC Unique ID is required";
    public static final String ANC_UNIQUE_ID_ALREADY_EXISTS = "ANC Unique ID already exists";
    public static final String NATIONAL_ID_ALREADY_EXISTS = "National ID already exists";
    public static final String NATIONAL_ID_MAX_LENGTH = "National ID must not exceed 50 characters";
    public static final String EDUCATION_LEVEL_MAX_LENGTH = "Education level must not exceed 50 characters";
    public static final String PHONE_PRIMARY_INVALID = "Primary phone number is invalid";
    public static final String PHONE_SECONDARY_INVALID = "Secondary phone number is invalid";
    public static final String NEXT_OF_KIN_PHONE_INVALID = "Next of kin phone number is invalid";
    public static final String LMP_DATE_INVALID = "Last Menstrual Period (LMP) date cannot be in the future";
    public static final String EDD_DATE_INVALID = "Expected Due Date (EDD) cannot be in the past";
    public static final String PREGNANCY_CONFIRMED_DATE_INVALID = "Pregnancy confirmed date cannot be in the future";
    public static final String HIV_TEST_DATE_INVALID = "HIV test date cannot be in the future";
    public static final String ART_START_DATE_INVALID = "ART start date cannot be in the future";
    public static final String LAST_VL_DATE_INVALID = "Last Viral Load (VL) date cannot be in the future";
    public static final String AGE_INVALID = "Age must be between 10 and 60 years";
    public static final String GRAVIDITY_INVALID = "Gravidity must be a non-negative number";
    public static final String PARITY_INVALID = "Parity must be a non-negative number";
    public static final String TERM_BIRTHS_INVALID = "Term births must be a non-negative number";
    public static final String PRETERM_BIRTHS_INVALID = "Preterm births must be a non-negative";
    public static final String OCCUPATION_MAX_LENGTH = "Occupation must not exceed 100 characters";
    public static final String ABORTIONS_INVALID = "Abortions must be a non-negative number";
    public static final String LIVING_CHILDREN_INVALID = "Living children must be a non-negative number";
}
