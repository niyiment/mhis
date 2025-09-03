package com.niyiment.mhis.facility.service;

import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import com.niyiment.mhis.facility.dto.FacilityCreateRequest;
import com.niyiment.mhis.facility.dto.FacilityResponse;
import com.niyiment.mhis.facility.dto.FacilityUpdateRequest;
import com.niyiment.mhis.facility.exception.FacilityNotFoundException;
import com.niyiment.mhis.facility.repository.FacilityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FacilityServiceTest {
    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private FacilityMapper facilityMapper;

    @Mock
    private FacilityValidationService facilityValidationService;

    @InjectMocks
    private FacilityServiceImpl facilityService;

    private Facility facility;
    private FacilityCreateRequest createRequest;
    private FacilityUpdateRequest updateRequest;
    private FacilityResponse facilityResponse;
    private UUID facilityId;

    @BeforeEach
    void setup() {
        facilityId = UUID.randomUUID();

        facility = Facility.builder()
                .id(facilityId)
                .facilityCode("FAC001")
                .facilityName("Test Hospital")
                .facilityType(FacilityType.HOSPITAL)
                .levelOfCare(LevelOfCare.TERTIARY)
                .state("FCT")
                .lga("Bwari")
                .contactPhone("+234123456789")
                .dhis2OrgUnitId("DHIS234")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        createRequest = new FacilityCreateRequest(
                "FAC001",
                "Test Hospital",
                FacilityType.HOSPITAL,
                LevelOfCare.TERTIARY,
                "FCT",
                "Bwari",
                "+234123456789",
                "DHIS234"
        );

        updateRequest = new FacilityUpdateRequest(
                "Test Hospital",
                FacilityType.HOSPITAL,
                LevelOfCare.TERTIARY,
                "FCT",
                "Bwari",
                "+234123456789",
                "DHIS234"
        );

        facilityResponse = new FacilityResponse(
                facilityId, facility.getFacilityCode(), facility.getFacilityName(),
          facility.getFacilityType(), facility.getLevelOfCare(),
          facility.getState(), facility.getLga(), facility.getContactPhone(),
          facility.getDhis2OrgUnitId(), facility.isActive(),
          facility.getCreatedAt(), facility.getUpdatedAt()
        );
    }

    @Test
    void createFacility_Success() {
        //Given
        when(facilityRepository.existsByFacilityCode("FAC001")).thenReturn(false);
        when(facilityMapper.toEntity(createRequest)).thenReturn(facility);
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);
        when(facilityMapper.toResponse(facility)).thenReturn(facilityResponse);

        //when
        FacilityResponse response = facilityService.createFacility(createRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.facilityCode()).isEqualTo(facility.getFacilityCode());
        assertThat(response.facilityName()).isEqualTo(facility.getFacilityName());

        verify(facilityRepository).existsByFacilityCode("FAC001");
        verify(facilityRepository).save(any(Facility.class));
        verify(facilityMapper).toEntity(createRequest);
        verify(facilityMapper).toResponse(facility);
    }

    @Test
    void updateFacility_Success() {
        //Given
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);
        when(facilityMapper.toResponse(any(Facility.class))).thenReturn(facilityResponse);

        //when
        FacilityResponse response = facilityService.updateFacility(facilityId, updateRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.facilityCode()).isEqualTo(facility.getFacilityCode());
        assertThat(response.facilityName()).isEqualTo(facility.getFacilityName());

        verify(facilityRepository).findById(facilityId);
        verify(facilityRepository).save(any(Facility.class));
        verify(facilityMapper).toResponse(any(Facility.class));
    }

    @Test
    void deactivateFacility_Success() {
        //Given
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);

        //When
        facilityService.deactivateFacility(facilityId);

        //Then
        assertThat(facility.isActive()).isFalse();
        verify(facilityRepository).findById(facilityId);
        verify(facilityRepository).save(any(Facility.class));
    }

    @Test
    void reactivateFacility_Success() {
        // Given
        facility.activate();
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);

        // when
        facilityService.reactivateFacility(facilityId);

        // then
        assertThat(facility.isActive()).isTrue();
        verify(facilityRepository).findById(facilityId);
        verify(facilityRepository).save(facility);
   }

   @Test
    void findById_NotFound_ThrowsException() {
        // Given
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> facilityService.findById(facilityId))
                .isInstanceOf(FacilityNotFoundException.class)
                .hasMessageContaining(facilityId.toString());
        verify(facilityRepository).findById(facilityId);
        verify(facilityMapper, never()).toResponse(any());
   }

   @Test
    void findById_Success() {
        // Given
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(facilityMapper.toResponse(facility)).thenReturn(facilityResponse);

        // When
        FacilityResponse response = facilityService.findById(facilityId);

        // Then
       assertThat(response).isNotNull();
        assertThat(response.facilityId()).isEqualTo(facilityId);
        verify(facilityRepository).findById(facilityId);
        verify(facilityMapper).toResponse(facility);
   }

    @Test
    void findActiveFacilities_Success() {
        when(facilityRepository.findByIsActiveTrue()).thenReturn(List.of(facility));
        when(facilityMapper.toResponse(facility)).thenReturn(facilityResponse);

        List<FacilityResponse> results = facilityService.findActiveFacilities();

        assertEquals(1, results.size());
        assertEquals("FAC001", results.get(0).facilityCode());
    }
}

