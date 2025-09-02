package com.niyiment.mhis.facility.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "facilities", indexes = {
        @Index(name = "idx_facility_code", columnList = "facility_code"),
        @Index(name = "idx_facility_state", columnList = "facility_state"),
        @Index(name = "idx_facility_lga", columnList = "facility_lga"),
        @Index(name = "idx_facility_type", columnList = "facility_type"),
        @Index(name = "idx_active", columnList = "is_active")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "facility_code", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Facility code is required")
    @Size(max = 20, message = "Facility code must not exceed 20 characters")
    private String facilityCode;

    @Column(name = "facility_name", nullable = false)
    @NotBlank(message = "Facility name is required")
    @Size(max = 255, message = "Facility name must not exceed 255 characters")
    private String facilityName;

    @Enumerated(EnumType.STRING)
    @Column(name = "facility_type", nullable = false, length = 50)
    private FacilityType facilityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_of_care", nullable = false, length = 20)
    private LevelOfCare levelOfCare;

    @Column(name = "state", nullable = false, length = 100)
    @NotBlank(message = "State is required")
    private String state;

    @Column(name = "lga", nullable = false, length = 100)
    @NotBlank(message = "LGA is required")
    private String lga;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "dhis2_org_unit_id", length = 50)
    private String dhis2OrgUnitId;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Facility facility = (Facility) o;
        return getId() != null && Objects.equals(getId(), facility.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
