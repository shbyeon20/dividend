package com.example.dividen.persist.entity;

import com.example.dividen.model.DividendInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "DIVIDEND")
@Getter
@ToString
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"companyId","date"})
})
public class DividendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private LocalDateTime date;

    private String dividend;

    public DividendEntity(Long companyId, DividendInfo dividendInfo) {
        this.companyId = companyId;
        this.date = dividendInfo.getDate();
        this.dividend = dividendInfo.getDividend();
    }
}
