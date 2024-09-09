package com.biorbac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AcademicUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long academicId;
    private String unitName;


    @ManyToOne
    @JsonIgnore
    private Student student;

    @ManyToMany(mappedBy = "academic_unit")
    @JsonIgnore
    private List<Institution> institutions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "academicUnit_fieldOfStudy_MAPPING", joinColumns = @JoinColumn(name = "academic_id"),
            inverseJoinColumns = @JoinColumn(name = "fos_id"))
    private List<FieldOfStudy> fieldOfStudies;

}
