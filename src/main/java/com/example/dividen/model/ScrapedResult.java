package com.example.dividen.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ScrapedResult {
    private Company company;

    private List<DividendInfo> dividendes;

    public ScrapedResult() {
        this.dividendes = new ArrayList<>();
    }
}
