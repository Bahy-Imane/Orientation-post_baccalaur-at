package com.biorbac.model;

import com.biorbac.enums.BacType;
import com.biorbac.enums.Interest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Student extends User {

    private LocalDate dateOfBirth;
    private double bacScore;
    private String location;

    @Enumerated(EnumType.STRING)
    private BacType bacType;

    @Enumerated(EnumType.STRING)
    private Interest interest;



    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "student")
    private List<Review> reviews;


    @OneToMany(mappedBy = "student")
    private List<FieldOfStudy> fieldOfStudies;
}
