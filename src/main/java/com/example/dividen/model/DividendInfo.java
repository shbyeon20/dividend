package com.example.dividen.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DividendInfo {
    private LocalDateTime date;
    private String dividend;
}
