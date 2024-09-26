package com.biorbac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;
    private String departmentName;



    @ManyToOne
    @JsonIgnore
    private Institution institution;


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<FieldOfStudy> fieldOfStudies;

}
