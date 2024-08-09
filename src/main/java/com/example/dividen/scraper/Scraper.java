package com.example.dividen.scraper;

import com.example.dividen.model.Company;
import com.example.dividen.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
