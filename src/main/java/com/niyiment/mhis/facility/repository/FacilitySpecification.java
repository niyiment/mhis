package com.niyiment.mhis.facility.repository;


import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.facility.domain.FacilityType;
import com.niyiment.mhis.facility.domain.LevelOfCare;
import com.niyiment.mhis.facility.dto.FacilitySearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FacilitySpecification {

    public static Specification<Facility> searchFacilities(FacilitySearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(request.searchTerm())) {
                String searchPattern = "%" + request.searchTerm().toLowerCase() + "%";
                Predicate nameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("facilityName")), searchPattern);
                Predicate codeMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("facilityCode")), searchPattern);
                predicates.add(criteriaBuilder.or(nameMatch, codeMatch));
            }

            if (StringUtils.hasText(request.state())) {
                predicates.add(criteriaBuilder.equal(root.get("state"), request.state()));
            }

            if (StringUtils.hasText(request.lga())) {
                predicates.add(criteriaBuilder.equal(root.get("lga"), request.lga()));
            }

            if (request.facilityType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("facilityType"), request.facilityType()));
            }

            if (request.levelOfCare() != null) {
                predicates.add(criteriaBuilder.equal(root.get("levelOfCare"), request.levelOfCare()));
            }
            if (request.isActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), request.isActive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Facility> hasSearchTern(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(searchTerm)) {
                return criteriaBuilder.conjunction();
            }
            String searchPattern = "%" + searchTerm.toLowerCase() + "%";
            Predicate nameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("facilityName")), searchPattern);
            Predicate codeMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("facilityCode")), searchPattern);
            return criteriaBuilder.or(nameMatch, codeMatch);
            };
    }

    public static Specification<Facility> hasState(String state) {
        return (root, query, criteriaBuilder) ->
                (!StringUtils.hasText(state)) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("state"), state);
    }

    public static Specification<Facility> hasLga(String lga) {
        return (root, query, criteriaBuilder) ->
                (!StringUtils.hasText(lga)) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("lga"), lga);
    }

    public static Specification<Facility> hasFacilityType(FacilityType facilityType) {
        return (root, query, criteriaBuilder) ->
                (facilityType != null)
                        ? criteriaBuilder.equal(root.get("facilityType"), facilityType)
                        : criteriaBuilder.conjunction();
    }

    public static Specification<Facility> hasLevelOfCare(LevelOfCare levelOfCare) {
        return (root, query, criteriaBuilder) ->
                (levelOfCare != null) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("
    }

    public static Specification<Facility> isActive(Boolean active) {
        return (root, query, criteriaBuilder) ->
                (active != null) ? criteriaBuilder.equal(root.get("isActive"), active) : criteriaBuilder.conjunction();
    }

}

