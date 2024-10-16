package com.biorbac.dto;


import com.biorbac.model.Student;
import com.biorbac.model.Institution;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private Long reviewId;
    private String comment;
    private Integer rating;
    private String userName;
    private Long institutionId;

}

