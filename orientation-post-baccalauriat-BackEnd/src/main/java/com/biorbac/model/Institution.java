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
    private String name;
    private String location;
    private String website;
    private String description;


    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;


    @ManyToOne
    @JsonIgnore
    private Student student;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "institution_academic", joinColumns = @JoinColumn(name = "institution_id"),
            inverseJoinColumns = @JoinColumn(name = "academic_id"))
    private List<AcademicUnit> academicUnits;



}
