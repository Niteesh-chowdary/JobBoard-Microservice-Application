package com.Niteesh.companyMicroservice.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long id;
    private String title;
    private String description;
    private double rating;
    private Long companyId;
}
