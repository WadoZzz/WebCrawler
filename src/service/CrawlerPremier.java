package service;

import entity.Ads;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class CrawlerPremier implements ICrawler {

    private static String page = "&page=";
    private static String URL_PATH = "https://premier.ua/";
    private static int countAds = 0;
    public static String getUrlPath() {
        return URL_PATH;
    }

    @Override
    public Set<Ads> parsePage(String query) {
        Set<Ads> adsPremierList = new LinkedHashSet<>();
        if (!adsIsFail(query)) {
            System.out.println("All page: " + getLastListPagination(query));

            for (int i = 1; i <= getLastListPagination(query); i++) {
                Document document = null;
                try {
                    document = Jsoup.connect(URL_PATH + "?q=" + query + page + i).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert document != null;
                Elements elements = document.select(".adv-item-content");

                elements.forEach(element -> {
                    Element element1 = element.child(0);
                    String title = element1.select(".adv-title").text();
                    String link = element.select("a[href]").attr("href");
                    String price = element.select("td.adv-price").text();
                    String data = element.select("td[width=226px]").text();

                    adsPremierList.add(new Ads(title, link, price, data));
                    countAds++;
                });

                System.out.println(document.baseUri());

                for (Ads ads : adsPremierList) {
                    System.out.println(ads.toStringPremier());
                }

                System.out.println("All page: " + "-> " + getLastListPagination(query));
                System.out.println("Current page: " + "-> " + i);
                System.out.println("Total ADS: " + countAds);
            }
        }
        return adsPremierList;
    }

    public int getLastListPagination(String query) {
        Document doc = null;
        try {
            doc = Jsoup.connect(URL_PATH + "?q=" + query).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
        Elements el = doc.select(".pagins");
        return Integer.parseInt(el.select("a[href]").last().text());
    }

    @Override
    public boolean adsIsFail(String query) {
        Document document = null;
        try {
            document = Jsoup.connect(URL_PATH + "?q=" + query).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert document != null;
        Elements elements = document.select(".adv-item-content");
        if (elements.hasClass(".pagins")) {
            System.out.println("No such messages, pls check query");
            return true;
        }
        return false;
    }
}