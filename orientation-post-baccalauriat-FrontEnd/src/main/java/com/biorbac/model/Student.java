package com.biorbac.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends User {

    private String major;
    private String graduationYear;
}
