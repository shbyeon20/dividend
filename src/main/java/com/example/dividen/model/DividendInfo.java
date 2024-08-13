package com.example.dividen.model;

import com.example.dividen.persist.entity.DividendEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
    private String dividend;

    public static DividendInfo fromEntity(DividendEntity dividendEntity){
        return DividendInfo.builder()
                .date(dividendEntity.getDate())
                .dividend(dividendEntity.getDividend())
                .build();
    }
}
