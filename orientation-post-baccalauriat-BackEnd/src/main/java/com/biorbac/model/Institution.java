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


    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;


    @ManyToOne
    @JsonIgnore
    private Student student;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Review> reviews;


    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Department> departments;


}
