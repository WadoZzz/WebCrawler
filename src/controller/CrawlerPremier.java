package controller;

import entity.AdsPremier;
import service.ICrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class CrawlerPremier implements ICrawler {

    private  String query;
    private static String page = "&page=";
    private static String URL_PATH = "https://premier.ua";
    private static int countAds = 0;
    public static String getUrlPath() {
        return URL_PATH;
    }
    public CrawlerPremier(String query) {
        this.query = "/?q="  + query;
    }

    @Override
    public void ParsePage() {
        Set<AdsPremier> adsPremierList = new LinkedHashSet<>();
        if (!ADSisFail()) {
            System.out.println("All page: " + getLastListPageination());

            for (int i = 1; i <= getLastListPageination(); i++) {
                Document document = null;
                try {
                    document = Jsoup.connect(URL_PATH + query + page + i).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Elements elements = document.select(".adv-item-content");

                elements.forEach(element -> {
                    Element element1 = element.child(0);
                    String title = element1.select(".adv-title").text();
                    String link = element.select("a[href]").attr("href");
                    String price = element.select("td.adv-price").text();
                    String data = element.select("td[width=226px]").text();

                    adsPremierList.add(new AdsPremier(title, link, price, data));
                    countAds++;
                });
                System.out.println(document.baseUri());
                adsPremierList.forEach(System.out::println);

                System.out.println("All page: " + "-> " + getLastListPageination());
                System.out.println("Current page: " + "-> " + i);
                System.out.println("Total ADS: " + countAds);
            }
        }
    }

    public int getLastListPageination() {
        Document doc = null;
        try {
            doc = Jsoup.connect(URL_PATH + query).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements el = doc.select(".pagins");
        return Integer.parseInt(el.select("a[href]").last().text());
    }

    @Override
    public boolean ADSisFail() {
        Document document = null;
        try {
            document = Jsoup.connect(URL_PATH + query).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = document.select(".adv-item-content");
        if (!elements.hasClass(".pagins")) {
            return false;
        } else {
            System.out.println("No such messages, pls check query");
            return true;
        }
    }
}
