package com.example.dividen.model;


import com.example.dividen.persist.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private String ticker;
    private String name;

public static Company fromEntity (CompanyEntity companyEntity){

    return Company.builder()
            .ticker(companyEntity.getTicker())
            .name(companyEntity.getName())
            .build();

    }
}

