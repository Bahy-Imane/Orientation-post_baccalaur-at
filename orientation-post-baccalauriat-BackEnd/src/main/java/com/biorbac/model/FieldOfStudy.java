package com.biorbac.model;

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
@AllArgsConstructor
@NoArgsConstructor
public class FieldOfStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fosId;
    private String name;
    private String description;
    private Double entryRequirement;
    private Integer numberOfStudents;



    @ManyToOne
    @JsonIgnore
    private Student student;

    @ManyToMany(mappedBy = "field_of_study")
    @JsonIgnore
    private List<AcademicUnit> academicUnits;
}
