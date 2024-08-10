package com.example.dividen.service;

import com.example.dividen.model.Company;
import com.example.dividen.model.DividendInfo;
import com.example.dividen.model.ScrapedResult;
import com.example.dividen.persist.CompanyRepository;
import com.example.dividen.persist.DividendRepository;
import com.example.dividen.persist.entity.CompanyEntity;
import com.example.dividen.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;


    public ScrapedResult getDividendByName(String companyName) {

        //1. 회사명으로 회사정보를 조회함

        CompanyEntity companyEntity = companyRepository.findByName(companyName).orElseThrow(() -> new RuntimeException("CompanyName " + "not found ->" + companyName));


        //2. 회사정보로 배당금 정보를 조회함'
        List<DividendEntity> dividendEntities = dividendRepository.findAllByCompanyId(companyEntity.getId()).orElseThrow(() -> new RuntimeException("CompanyID not found ->" + companyEntity.getName()));


        //3. 회사정보와 배당금정보를 ScrapedResult로 담아서 return
        List<DividendInfo> dividendInfos =
                dividendEntities.stream().map(DividendInfo::fromEntity)
                        .collect(Collectors.toList());


        return new ScrapedResult(Company.fromEntity(companyEntity), dividendInfos);
    }
}
