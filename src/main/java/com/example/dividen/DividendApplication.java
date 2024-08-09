package com.example.dividen;

import com.example.dividen.model.Company;
import com.example.dividen.model.ScrapedResult;
import com.example.dividen.scraper.YahooFinanceScraper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

//@SpringBootApplication
public class DividendApplication {

    public static void main(String[] args) {
      //  SpringApplication.run(DividendApplication.class, args);
        YahooFinanceScraper yahooFinanceScraper = new YahooFinanceScraper();
        ScrapedResult dividen =
                yahooFinanceScraper.scrap(Company.builder().ticker("O").build());
        String companyName =
                String.valueOf(yahooFinanceScraper.scrapCompanyByTicker("COKE"));

        System.out.println(companyName);
    }

}


