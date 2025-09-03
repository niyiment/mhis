package com.niyiment.mhis.facility.controller;

import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import com.niyiment.mhis.facility.dto.*;
import com.niyiment.mhis.facility.service.FacilityService;
import com.niyiment.mhis.shared.dto.CustomApiResponse;
import com.niyiment.mhis.shared.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facilities")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Facility Management", description = "APIs for managing healthcare facilities")
public class FacilityController {
  private final FacilityService facilityService;

  @PostMapping
  @Operation(summary = "Create a new facility", description = "Create a new healthcare facility")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Facility created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "409", description = "Facility code already exists")
      })
  public ResponseEntity<CustomApiResponse<FacilityResponse>> createFacility(
      @Valid @RequestBody FacilityCreateRequest request) {
    log.info("Creating facility with code: {}", request.facilityCode());
    FacilityResponse response = facilityService.createFacility(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CustomApiResponse.success("Facility created successfully", response));
  }

  @PutMapping("/{facilityId}")
  @Operation(
      summary = "Update an existing facility",
      description = "Update an existing healthcare facility")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Facility updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Facility not found"),
      })
  public ResponseEntity<CustomApiResponse<FacilityResponse>> updateFacility(
      @Parameter(description = "Facility ID") @PathVariable UUID facilityId,
      @Valid @RequestBody FacilityUpdateRequest request) {
    log.info("Updating facility with ID: {}", facilityId);

    FacilityResponse response = facilityService.updateFacility(facilityId, request);

    return ResponseEntity.ok(CustomApiResponse.success("Facility updated successfully", response));
  }

  @GetMapping("/{facilityId}")
  @Operation(summary = "Get a facility by ID", description = "Get a healthcare facility by its ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Facility found"),
        @ApiResponse(responseCode = "404", description = "Facility not found")
      })
  public ResponseEntity<CustomApiResponse<FacilityResponse>> getFacilityById(
      @Parameter(description = "Facility ID") @PathVariable UUID facilityId) {
    log.debug("Getting facility by ID: {}", facilityId);
    FacilityResponse response = facilityService.findById(facilityId);

    return ResponseEntity.ok(CustomApiResponse.success("Facility found", response));
  }

  @GetMapping("/code/{facilityCode}")
  @Operation(
      summary = "Get a facility by code",
      description = "Get a healthcare facility by its code")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Facility found"),
        @ApiResponse(responseCode = "404", description = "Facility not found")
      })
  public ResponseEntity<CustomApiResponse<FacilityResponse>> getFacilityByCode(
      @Parameter(description = "Facility ID") @PathVariable String facilityCode) {
    log.debug("Getting facility by code: {}", facilityCode);
    FacilityResponse response = facilityService.findByFacilityCode(facilityCode);

    return ResponseEntity.ok(CustomApiResponse.success("Facility found", response));
  }

  @GetMapping("/search")
  @Operation(
      summary = "Search facilities",
      description = "Search healthcare facilities by name or code")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Facilities found")})
  public ResponseEntity<CustomApiResponse<PageResponse<FacilityResponse>>> searchFacilities(
      @Parameter(description = "Search term") @RequestParam(required = false) String searchTerm,
      @Parameter(description = "State filter") @RequestParam(required = false) String state,
      @Parameter(description = "LGA filter") @RequestParam(required = false) String lga,
      @Parameter(description = "Facility type filter") @RequestParam(required = false)
          FacilityType facilityType,
      @Parameter(description = "Level of care filter") @RequestParam(required = false)
          LevelOfCare levelOfCare,
      @Parameter(description = "Active status filter") @RequestParam(required = false)
          Boolean isActive,
      @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
      @Parameter(description = "Sort field") @RequestParam(defaultValue = "facilityName")
          String sortBy,
      @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc")
          String sortDirection) {

    FacilitySearchRequest request =
        new FacilitySearchRequest(
            searchTerm,
            state,
            lga,
            facilityType,
            levelOfCare,
            isActive,
            page,
            size,
            sortBy,
            sortDirection);

    Page<FacilityResponse> result = facilityService.searchFacilities(request);
    PageResponse<FacilityResponse> pageResponse = PageResponse.of(result);

    return ResponseEntity.ok(
        CustomApiResponse.success("Search completed successfully", pageResponse));
  }

  @GetMapping("/active")
  @Operation(
      summary = "Get all active facilities",
      description = "Get a list of all active healthcare facilities")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Facilities found")})
  public ResponseEntity<CustomApiResponse<List<FacilityResponse>>> getActiveFacilities() {
    log.debug("Getting all active facilities");
    List<FacilityResponse> response = facilityService.findActiveFacilities();
    return ResponseEntity.ok(CustomApiResponse.success("Active facilities found", response));
  }

  @GetMapping("/state/{state}")
  @Operation(
      summary = "Get facilities by state",
      description = "Retrieves facilities in a specific state")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Facilities found")})
  public ResponseEntity<CustomApiResponse<List<FacilityResponse>>> getFacilitiesByState(
      @Parameter(description = "state") @PathVariable String state,
      @Parameter(description = "active status filter")
          @RequestParam(required = false, defaultValue = "true")
          boolean isActive) {
    log.debug("Getting facilities by state: {}, activeOnly: {}", state, isActive);
    List<FacilityResponse> response = facilityService.findByState(state, isActive);
    return ResponseEntity.ok(CustomApiResponse.success("Facilities retrieved by state", response));
  }

  @GetMapping("/lga/{lga}")
  @Operation(
      summary = "Get facilities by lga",
      description = "Retrieves facilities in a specific LGA")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Facilities found")})
  public ResponseEntity<CustomApiResponse<List<FacilityResponse>>> getFacilitiesByLga(
      @Parameter(description = "lga") @PathVariable String lga,
      @Parameter(description = "active status filter")
          @RequestParam(required = false, defaultValue = "true")
          boolean isActive) {
    log.debug("Getting facilities by lga: {}, activeOnly: {}", lga, isActive);
    List<FacilityResponse> response = facilityService.findByLga(lga, isActive);
    return ResponseEntity.ok(CustomApiResponse.success("Facilities retrieved by lga", response));
  }

  @GetMapping("/type/{facilityType}")
  @Operation(
      summary = "Get facilities by facilityType",
      description = "Retrieves facilities in a specific type")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Facilities found")})
  public ResponseEntity<CustomApiResponse<List<FacilityResponse>>> getFacilitiesByType(
      @Parameter(description = "facility type") @PathVariable FacilityType facilityType,
      @Parameter(description = "active status filter")
          @RequestParam(required = false, defaultValue = "true")
          boolean isActive) {
    log.debug("Getting facilities by type: {}, activeOnly: {}", facilityType, isActive);
    List<FacilityResponse> response = facilityService.findByFacilityType(facilityType, isActive);
    return ResponseEntity.ok(CustomApiResponse.success("Facilities retrieved by lga", response));
  }

  @GetMapping("/level/{levelOfCare}")
  @Operation(
      summary = "Get facilities by levelOfCare",
      description = "Retrieves facilities in a specific level of care")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Facilities found")})
  public ResponseEntity<CustomApiResponse<List<FacilityResponse>>> getFacilitiesByLevelOfCare(
      @Parameter(description = "level of care") @PathVariable String levelOfCare,
      @Parameter(description = "active status filter")
          @RequestParam(required = false, defaultValue = "true")
          boolean isActive) {
    log.debug("Getting facilities by level of care: {}, activeOnly: {}", levelOfCare, isActive);
    List<FacilityResponse> response = facilityService.findByLga(levelOfCare, isActive);
    return ResponseEntity.ok(
        CustomApiResponse.success("Facilities retrieved by level of care", response));
  }

  @PatchMapping("/{facilityId}/deactivate")
  @Operation(summary = "Deactivate a facility", description = "Deactivates a healthcare facility")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Facility deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Facility not found")
      })
  public ResponseEntity<CustomApiResponse<Void>> deactivateFacility(
      @Parameter(description = "Facility ID") @PathVariable UUID facilityId) {
    log.info("Deactivating facility with ID: {}", facilityId);
    facilityService.deactivateFacility(facilityId);
    return ResponseEntity.ok(CustomApiResponse.success("Facility deactivated successfully", null));
  }

  @PatchMapping("/{facilityId}/activate")
  @Operation(summary = "Activate a facility", description = "Activates a healthcare facility")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Facility activated successfully"),
        @ApiResponse(responseCode = "404", description = "Facility not found")
      })
  public ResponseEntity<CustomApiResponse<Void>> activateFacility(
      @Parameter(description = "Facility ID") @PathVariable UUID facilityId) {
    log.info("Activating facility with ID: {}", facilityId);
    facilityService.reactivateFacility(facilityId);

    return ResponseEntity.ok(CustomApiResponse.success("Facility activated successfully", null));
  }

  @GetMapping("/{facilityId}/statistics")
  @Operation(
      summary = "Get facility statistics",
      description = "Retrieves statistics for a healthcare facility")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Facility not found")
      })
  public ResponseEntity<CustomApiResponse<FacilityStatisticsResponse>> getFacilityStatistics(
      @Parameter(description = "Facility ID") @PathVariable UUID facilityId) {
    log.debug("Getting statistics for facility ID: {}", facilityId);
    FacilityStatisticsResponse response = facilityService.getFacilityStatistics(facilityId);

    return ResponseEntity.ok(
        CustomApiResponse.success("Statistics retrieved successfully", response));
  }

  @GetMapping("/count/active")
  @Operation(
      summary = "Get active facility count",
      description = "Retrieves the count of active healthcare facilities")
  public ResponseEntity<CustomApiResponse<Long>> getActiveFacilityCount() {
    log.debug("Getting active facility count");
    long count = facilityService.getActiveFacilityCount();

    return ResponseEntity.ok(
        CustomApiResponse.success("Active facility count retrieved successfully", count));
  }

  @GetMapping("/count/state/{state}")
  @Operation(
          summary = "Get facility count by state",
          description = "Retrieves the count of active healthcare facilities in a state")
  public ResponseEntity<CustomApiResponse<Long>> getFacilityCountByState(
          @Parameter(description = "state") @PathVariable String state
  ) {
    log.debug("Getting active facility count for state: {}", state);
    long count = facilityService.getActiveFacilityCountByState(state);

    return ResponseEntity.ok(
            CustomApiResponse.success("Active facility count retrieved successfully", count));
  }
}
