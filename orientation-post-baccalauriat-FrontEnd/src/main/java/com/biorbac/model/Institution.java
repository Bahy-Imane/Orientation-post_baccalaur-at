package com.biorbac.model;

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

    private String name;
    private String subject;
    private Long grade;
    private String location;
    private String website;
    private String description;
    private String admissionRequirements;
    private String tuitionFees;
    private String housingOptions;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
