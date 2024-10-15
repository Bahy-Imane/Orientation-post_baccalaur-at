package com.biorbac.model;

import com.biorbac.enums.BacType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    private double minimumBacNote;
    private String departmentName;
    private String fieldOfStudyLogo;

    @Enumerated(EnumType.STRING)
    private BacType bacTypeRequired;

    @ManyToOne
    @JsonIgnore
    private Institution institution ;


}
