package com.biorbac.model;

import com.biorbac.enums.Specialization;
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

    private double baccalaureateScore;
    private String location;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @OneToMany(mappedBy = "student")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "student")
    private List<Comment> comments;

    @OneToMany(mappedBy = "student")
    private List<Recommendation> recommendations;
}
