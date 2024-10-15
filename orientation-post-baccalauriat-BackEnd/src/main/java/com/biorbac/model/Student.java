package com.biorbac.model;

import com.biorbac.enums.BacType;
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


    @OneToMany(mappedBy = "student")
    private List<Review> reviews;


}
