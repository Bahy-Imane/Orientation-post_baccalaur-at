package com.biorbac.model;

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
    private String bacTypeRequired;  // Le type de Bac requis pour ce domaine
    private double minimumBacNote;  // Note minimale requise pour accéder à ce domaine
    private String matchingInterest;  // Intérêts correspondants pour ce domaine



    @ManyToOne
    @JsonIgnore
    private Department department ;
}
