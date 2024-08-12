package com.example.dividen.service;

import com.example.dividen.model.Company;
import com.example.dividen.model.ScrapedResult;
import com.example.dividen.persist.CompanyRepository;
import com.example.dividen.persist.DividendRepository;
import com.example.dividen.persist.entity.CompanyEntity;
import com.example.dividen.persist.entity.DividendEntity;
import com.example.dividen.scraper.YahooFinanceScraper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    private final Trie trie;

    private final YahooFinanceScraper yahooFinanceScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker) {
        if (companyRepository.existsByTicker(ticker)) {
            throw new RuntimeException("already exists ticker ->" + ticker);
        }
        return this.storeCompanyAndDividend(ticker);
    }

    private Company storeCompanyAndDividend(String ticker) {
        // ticker을 기준으로 회사를 scraping
        Company company = yahooFinanceScraper.scrapCompanyByTicker(ticker);
        if (ObjectUtils.isEmpty(company)) {
            throw new RuntimeException("Failed to scrap company by ticker: " + ticker);
        }


        // 성공할시에 dividened도 같이 scraping

        ScrapedResult scrapedResult = yahooFinanceScraper.scrap(company);

        // 성공시에 repository에 저장후 company entity를 반환해줌
        CompanyEntity companyEntity = companyRepository.save(new CompanyEntity(company));
        List<DividendEntity> dividendEntities
                = scrapedResult.getDividends().stream().map(dividendInfo
                        -> new DividendEntity(companyEntity.getId(), dividendInfo))
                .collect(Collectors.toList());

        dividendRepository.saveAll(dividendEntities);

        return company;

    }

    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public void addAutoCompleteKeyword(String keyword) {
        trie.put(keyword, null);
    }


    public List<String> autocompleteKeyword(String keyword) {
        return (List<String>) trie.prefixMap(keyword).keySet().stream()
                .collect(Collectors.toList());
    }

    public List<String> getCompanyNameByKeyword(String keyword) {
        Pageable limit = PageRequest.of(0, 10);
        return companyRepository.findAllByNameStartingWithIgnoreCase
                        (keyword, limit).stream()
                .map(CompanyEntity::getName)
                .collect(Collectors.toList());
    }

    public void deleteAutoCompleteKeyword(String keyword) {
        trie.remove(keyword);
    }
}
