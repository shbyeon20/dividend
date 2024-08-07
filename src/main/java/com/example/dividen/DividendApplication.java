package com.example.dividen;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

//@SpringBootApplication
public class DividendApplication {

    public static void main(String[] args) {
        // SpringApplication.run(DividenApplication.class, args);

        try {
            Connection connect = Jsoup.connect("https://finance.yahoo.com/" +
                    "quote/COKE/history/?frequency=1mo&period1=99153000&period2=1722993803");
            Document document = connect.get();
            Elements elements = document.
                    getElementsByClass("table yf-ewueuo");
            // 유일한 matching element 산출
            Element elementcontents = elements.get(0);

            // table의 children -> children 중 tbody -> tbody의 요소들 선택
            Elements tbodies = elementcontents.children().get(1).children();

            for (Element tbody : tbodies) {
                if(tbody.text().endsWith("Dividend")){
                    String[] splitTbody = tbody.text().split(" ");
                    String month = splitTbody[0];
                    String day = splitTbody[1].replace(",", "");
                    String year = splitTbody[2];
                    String diviend = splitTbody[3];
                    System.out.println(year + "/" + month + "/" + day + "->" + diviend);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


