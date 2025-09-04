package com.niyiment.mhis.mother.mapper;


import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.mother.domain.Mother;
import com.niyiment.mhis.mother.dto.MotherCreateRequest;
import com.niyiment.mhis.mother.dto.MotherResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Mother entity and DTOs
 */
@Component
public class MotherMapper {

    public Mother toEntity(MotherCreateRequest request) {
        if (request == null) {
            return null;
        }

        return Mother.builder()
                .firstName(request.firstName())
                .middleName(request.middleName())
                .lastName(request.lastName())
                .dateOfBirth(request.dateOfBirth())
                .nationalId(request.nationalId())
                .maritalStatus(request.maritalStatus())
                .educationLevel(request.educationLevel())
                .occupation(request.occupation())

                .phonePrimary(request.phonePrimary())
                .phoneSecondary(request.phoneSecondary())
                .addressLine1(request.addressLine1())
                .addressLine2(request.addressLine2())
                .ward(request.ward())
                .state(request.state())
                .lga(request.lga())

                .nextOfKinName(request.nextOfKinName())
                .nextOfKinPhone(request.nextOfKinPhone())
                .nextOfKinRelationship(request.nextOfKinRelationship())

                .gravidity(request.gravidity())
                .parity(request.parity())
                .termBirths(request.termBirths())
                .pretermBirths(request.pretermBirths())
                .abortions(request.abortions())
                .livingChildren(request.livingChildren())

                .previousCs(request.previousCs())
                .previousPph(request.previousPph())
                .previousEclampsia(request.previousEclampsia())
                .previousStillbirth(request.previousStillbirth())
                .chronicConditions(request.chronicConditions())
                .allergies(request.allergies())

                .lmpDate(request.lmpDate())
                .eddDate(request.eddDate())
                .pregnancyConfirmedDate(request.pregnancyConfirmedDate())

                .hivStatus(request.hivStatus())
                .hivTestDate(request.hivTestDate())
                .artStatus(request.artStatus())
                .artStartDate(request.artStartDate())
                .currentArtRegimen(request.currentArtRegimen())
                .lastVlResult(request.lastVlResult())
                .lastVlDate(request.lastVlDate())
                .vlSuppressed(request.vlSuppressed())

                .partnerHivStatus(request.partnerHivStatus())
                .partnerTested(request.partnerTested())
                .partnerNotificationDone(request.partnerNotificationDone())

                .facility(request.facilityId() != null ?
                        Facility.builder().id(request.facilityId()).build() : null)
                .build();
    }

    public MotherResponse toResponse(Mother mother) {
        if (mother == null) {
            return null;
        }

        return MotherResponse.builder()
                .id(mother.getId())
                .ancUniqueId(mother.getAncUniqueId())
                .firstName(mother.getFirstName())
                .middleName(mother.getMiddleName())
                .lastName(mother.getLastName())
                .dateOfBirth(mother.getDateOfBirth())
                .nationalId(mother.getNationalId())
                .maritalStatus(mother.getMaritalStatus())
                .educationLevel(mother.getEducationLevel())
                .occupation(mother.getOccupation())

                .phonePrimary(mother.getPhonePrimary())
                .phoneSecondary(mother.getPhoneSecondary())
                .addressLine1(mother.getAddressLine1())
                .addressLine2(mother.getAddressLine2())
                .ward(mother.getWard())
                .state(mother.getState())
                .lga(mother.getLga())

                .nextOfKinName(mother.getNextOfKinName())
                .nextOfKinPhone(mother.getNextOfKinPhone())
                .nextOfKinRelationship(mother.getNextOfKinRelationship())

                .gravidity(mother.getGravidity())
                .parity(mother.getParity())
                .termBirths(mother.getTermBirths())
                .pretermBirths(mother.getPretermBirths())
                .abortions(mother.getAbortions())
                .livingChildren(mother.getLivingChildren())

                .previousCs(mother.getPreviousCs())
                .previousPph(mother.getPreviousPph())
                .previousEclampsia(mother.getPreviousEclampsia())
                .previousStillbirth(mother.getPreviousStillbirth())
                .chronicConditions(mother.getChronicConditions())
                .allergies(mother.getAllergies())

                .lmpDate(mother.getLmpDate())
                .eddDate(mother.getEddDate())
                .pregnancyConfirmedDate(mother.getPregnancyConfirmedDate())

                .hivStatus(mother.getHivStatus())
                .hivTestDate(mother.getHivTestDate())
                .artStatus(mother.getArtStatus())
                .artStartDate(mother.getArtStartDate())
                .currentArtRegimen(mother.getCurrentArtRegimen())
                .lastVlResult(mother.getLastVlResult())
                .lastVlDate(mother.getLastVlDate())
                .vlSuppressed(mother.getVlSuppressed())

                .partnerHivStatus(mother.getPartnerHivStatus())
                .partnerTested(mother.getPartnerTested())
                .partnerNotificationDone(mother.getPartnerNotificationDone())

                .facilityId(mother.getFacility() != null ? mother.getFacility().getId() : null)
                .firstName(mother.getFacility() != null ? mother.getFacility().getFacilityName() : null)
                .build();
    }
}
