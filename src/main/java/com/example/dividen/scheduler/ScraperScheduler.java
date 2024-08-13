package com.example.dividen.scheduler;

import com.example.dividen.model.Company;
import com.example.dividen.model.ScrapedResult;
import com.example.dividen.persist.CompanyRepository;
import com.example.dividen.persist.DividendRepository;
import com.example.dividen.persist.entity.CompanyEntity;
import com.example.dividen.persist.entity.DividendEntity;
import com.example.dividen.scraper.YahooFinanceScraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class ScraperScheduler {

    private final CompanyRepository companyRepository;

    private final DividendRepository dividendRepository;

    private final YahooFinanceScraper yahooFinanceScraper;

    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("scraping scheduler started");
        // 저장된 회사 리스트 조회
        List<CompanyEntity> companyEntities = companyRepository.findAll();

        // 회사마다 배당금 정보를 새로 스크래핑
        for (CompanyEntity companyEntity : companyEntities) {
            ScrapedResult scrapedResult = yahooFinanceScraper.scrap(Company.fromEntity(companyEntity));
            log.info("scraping scheduler started : companyEntity = {}", companyEntity);
            // scraperesult가 db에 존재하는지 확인
            scrapedResult.getDividends().stream()
                    .map(dividendInfo -> new DividendEntity(companyEntity.getId(), dividendInfo))
                    .forEach(e -> {
                        boolean existsByCompanyIdAndDate =
                                dividendRepository.existsByCompanyIdAndDate(
                                        e.getCompanyId(), e.getDate());
                        if (!existsByCompanyIdAndDate) {
                            dividendRepository.save(e);
                        }
                    });
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }



    }
}
