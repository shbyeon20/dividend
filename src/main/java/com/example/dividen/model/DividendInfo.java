package com.example.dividen.model;

import com.example.dividen.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DividendInfo {
    private LocalDateTime date;
    private String dividend;

    public static DividendInfo fromEntity(DividendEntity dividendEntity){
        return DividendInfo.builder()
                .date(dividendEntity.getDate())
                .dividend(dividendEntity.getDividend())
                .build();
    }
}
