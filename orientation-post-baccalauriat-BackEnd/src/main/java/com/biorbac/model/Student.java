package com.biorbac.model;

import com.biorbac.enums.BacType;
import com.biorbac.enums.Interest;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Student extends User {

    private double bacScore;
    private String location;

    @Enumerated(EnumType.STRING)
    private BacType bacType;

    @Enumerated(EnumType.STRING)
    private Interest interest;



    @OneToMany(mappedBy = "student")
    private List<Institution> institutions;

    @OneToMany(mappedBy = "student")
    private List<AcademicUnit> academicUnits;

    @OneToMany(mappedBy = "student")
    private List<FieldOfStudy> fieldOfStudies;

    @OneToMany(mappedBy = "student")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "student")
    private List<Comment> comments;

    @OneToMany(mappedBy = "student")
    private List<Recommendation> recommendations;
}
