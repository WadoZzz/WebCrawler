package controller;

import entity.AdsOLX;
import city.City;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.ICrawler;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class CrawlerOLX implements ICrawler {

    private City city; //city ​​selection
    private String query; //search query
    private String page = "?page=";
    private String URL_PATH;
    private int countAds = 0;

    public CrawlerOLX(String query, City city) {
        this.city = city;
        this.query = query;
        URL_PATH = "https://www.olx.ua/" + this.city + "/q-" + this.query + "/";
    }

    public CrawlerOLX(String query) {
        this.query = query;
        URL_PATH = "https://www.olx.ua/" + City.list + "/q-" + this.query + "/";
    }

    /**
     * Find elements on current the page
     */


    @Override
    public void ParsePage() {
        Set<AdsOLX> adsList = new LinkedHashSet<>();

        if (!ADSisFail()) {
            System.out.println("All page: " + getLastListPageination());

            for (int i = 1; i <= getLastListPageination(); i++) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(URL_PATH + page + i).timeout(10000).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Elements elements = doc.getElementsByAttributeValue("class", "wrap");

                elements.forEach(element -> {
                    Element element1 = element.child(0);
                    String title = element1.select("h3 a").text();
                    String link = element.select("a[href]").attr("href");
                    String price = element1.select("p.price").text();
                    String date = element.select(".bottom-cell").text();

                    adsList.add(new AdsOLX(title, link, price, date));
                    countAds++;
                });
                System.out.println(doc.baseUri());
                adsList.forEach(System.out::println);

                System.out.println("All page: " + "-> " + getLastListPageination());
                System.out.println("Current page: " + "-> " + i);
                System.out.println("Total ADS: " + countAds);
            }
        }
    }

    /**
     * @return count pagination on page
     */
    @Override
    public int getLastListPageination() {
        Document doc = null;
        try {
            doc = Jsoup.connect(URL_PATH).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements el = doc.getElementsByAttributeValue("class", "item fleft");
        if (el.hasClass("item fleft")) {
            return Integer.parseInt(el.select("span").last().text());
        } else {
            return 1;
        }
    }

    /**
     * @return was the right request
     */
    @Override
    public boolean ADSisFail() {
        Document document = null;
        try {
            document = Jsoup.connect(URL_PATH).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements el = document.getElementsByAttributeValue("class", "c41 lheight24");
        if (el.hasClass("lheight24")) {
            System.out.println("No such messages, pls check query");
            return true;
        } else {
            return false;
        }
    }
}