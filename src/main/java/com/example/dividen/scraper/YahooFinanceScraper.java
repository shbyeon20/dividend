package com.example.dividen.scraper;

import com.example.dividen.model.Company;
import com.example.dividen.model.DividendInfo;
import com.example.dividen.model.ScrapedResult;
import com.example.dividen.model.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YahooFinanceScraper implements Scraper {

    private static final String STATISTICS_URL = "https://finance.yahoo.com/" +
            "quote/%s/history/?frequency=1mo&period1=%d&period2" +
            "=%d";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/" +
            "quote/%s";

    private static final long START_TIME = 86400;


    @Override
    public ScrapedResult scrap(Company company) {

        ScrapedResult scrapedResult = new ScrapedResult();
        scrapedResult.setCompany(company);

        try {
            long end = System.currentTimeMillis()/1000;

            String url = STATISTICS_URL.formatted(company.getTicker(), START_TIME,end);
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            Elements parsingClass = document.
                    getElementsByClass("table yf-ewueuo");
            // 유일한 matching element 산출
            Element tableElement = parsingClass.get(0);

            // table children -> children 중 tbody -> t-body 요소들 선택
            Elements tBodies = tableElement.children().get(1).children();


            List<DividendInfo> dividendInfos = new ArrayList<>();
            for (Element tbody : tBodies) {
                if (tbody.text().endsWith("Dividend")) {
                    String[] splitTbody = tbody.text().split(" ");
                    int month = Month.stringToNumber(splitTbody[0]);
                    int day = Integer.parseInt(splitTbody[1].replace(",", ""));
                    int year = Integer.parseInt(splitTbody[2]);
                    String dividend = splitTbody[3];

                    dividendInfos.add(DividendInfo.builder()
                                    .dividend(dividend)
                                    .date(LocalDateTime.of(year,month,day,0,0))
                            .build());
                }
            }
            scrapedResult.setDividendes(dividendInfos);

        } catch (
                IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        return scrapedResult;
    }

    @Override
    public Company scrapCompanyByTicker(String ticker) {
        try {
            String url = SUMMARY_URL.formatted(ticker);
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            Element titleEle = document.getElementsByTag("title").get(0);
            String title = titleEle.text().split(" \\(")[0].trim();

            return Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
