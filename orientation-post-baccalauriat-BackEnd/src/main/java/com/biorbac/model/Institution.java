package com.biorbac.model;

import com.biorbac.enums.InstitutionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Institution {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long institutionId;
        private String institutionName;
        private String description;
        private String address;
        private String website;
        private Double averageRating;
        private String institutionLogo;


    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;


    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Review> reviews;


    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<FieldOfStudy> fieldOfStudies;


}
