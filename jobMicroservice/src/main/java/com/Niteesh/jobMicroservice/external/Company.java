package com.Niteesh.jobMicroservice.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private Long id;
    private String name;
    private String description;
    private String location;
    List<Review> reviewList;
}
