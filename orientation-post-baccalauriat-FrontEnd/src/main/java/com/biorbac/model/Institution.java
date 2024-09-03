package com.biorbac.model;

import com.biorbac.enums.InstitutionType;
import com.biorbac.enums.Specialization;
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
    private String ville;
    private String website;
    private String description;
    private String conditionsAdmission;
    private String emailContact;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;


    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
