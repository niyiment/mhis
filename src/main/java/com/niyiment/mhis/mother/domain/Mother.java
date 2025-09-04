package com.niyiment.mhis.mother.domain;


import com.niyiment.mhis.facility.domain.Facility;
import com.niyiment.mhis.shared.converter.JsonbConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mothers")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mother {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "anc_unique_id", unique = true, nullable = false, length = 50)
    @NotBlank
    private String ancUniqueId;

    @Column(name = "national_id", unique = true, length = 50)
    private String nationalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    @NotNull
    private Facility facility;

    @Column(name = "first_name", nullable = false, length = 100)
    @NotBlank
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotBlank
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @Column(name = "age_years")
    private Integer ageYears;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", length = 20)
    private MaritalStatus maritalStatus;

    @Column(name = "education_level", length = 50)
    private String educationLevel;

    @Column(name = "occupation", length = 100)
    private String occupation;

    // Contact Information
    @Column(name = "phone_primary", length = 20)
    private String phonePrimary;

    @Column(name = "phone_secondary", length = 20)
    private String phoneSecondary;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "ward", length = 100)
    private String ward;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "lga", length = 100)
    private String lga;

    // Emergency Contact
    @Column(name = "next_of_kin_name")
    private String nextOfKinName;

    @Column(name = "next_of_kin_phone", length = 20)
    private String nextOfKinPhone;

    @Column(name = "next_of_kin_relationship", length = 50)
    private String nextOfKinRelationship;

    // Obstetric History
    @Column(name = "gravidity")
    @Builder.Default
    private Integer gravidity = 0;

    @Column(name = "parity")
    @Builder.Default
    private Integer parity = 0;

    @Column(name = "term_births")
    @Builder.Default
    private Integer termBirths = 0;

    @Column(name = "preterm_births")
    @Builder.Default
    private Integer pretermBirths = 0;

    @Column(name = "abortions")
    @Builder.Default
    private Integer abortions = 0;

    @Column(name = "living_children")
    @Builder.Default
    private Integer livingChildren = 0;

    // Risk Factors and History
    @Column(name = "previous_cs")
    @Builder.Default
    private Boolean previousCs = false;

    @Column(name = "previous_pph")
    @Builder.Default
    private Boolean previousPph = false;

    @Column(name = "previous_eclampsia")
    @Builder.Default
    private Boolean previousEclampsia = false;

    @Column(name = "previous_stillbirth")
    @Builder.Default
    private Boolean previousStillbirth = false;

    @Convert(converter = JsonbConverter.class)
    @Column(name = "chronic_conditions", columnDefinition = "jsonb")
    private List<String> chronicConditions;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    // Current Pregnancy Details
    @Column(name = "lmp_date")
    private LocalDate lmpDate;

    @Column(name = "edd_date")
    private LocalDate eddDate;

    @Column(name = "pregnancy_confirmed_date")
    private LocalDate pregnancyConfirmedDate;

    // HIV/PMTCT Related
    @Enumerated(EnumType.STRING)
    @Column(name = "hiv_status", length = 20)
    private HivStatus hivStatus;

    @Column(name = "hiv_test_date")
    private LocalDate hivTestDate;

    @Column(name = "art_start_date")
    private LocalDate artStartDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "art_status", length = 20)
    private ArtStatus artStatus;

    @Column(name = "current_art_regimen", length = 100)
    private String currentArtRegimen;

    @Column(name = "last_vl_result")
    private Integer lastVlResult;

    @Column(name = "last_vl_date")
    private LocalDate lastVlDate;

    @Column(name = "vl_suppressed")
    private Boolean vlSuppressed;

    // Partner Information
    @Enumerated(EnumType.STRING)
    @Column(name = "partner_hiv_status", length = 20)
    private HivStatus partnerHivStatus;

    @Column(name = "partner_tested")
    @Builder.Default
    private Boolean partnerTested = false;

    @Column(name = "partner_notification_done")
    @Builder.Default
    private Boolean partnerNotificationDone = false;

    // System Fields
    @Column(name = "registration_date")
    @Builder.Default
    private LocalDate registrationDate = LocalDate.now();

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
