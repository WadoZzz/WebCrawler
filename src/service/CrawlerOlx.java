package service;

import city.City;
import entity.Ads;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class CrawlerOlx implements ICrawler {

	private City city = City.LIST;
	private String page = "/?page=";
	private static String URL_PATH = "https://www.olx.ua/";
	private int countAds = 0;
	private static Document doc = null;

	try
	{
		doc = Jsoup.connect(URL_PATH).timeout(10000).get();
	}catch(
	IOException ex)
	{
		ex.getMessage();
	}

	/**
     * Find elements on current the page
     */
    @Override
    public Set<Ads> parsePage(String query) {
        Set<Ads> adsOLXList = new LinkedHashSet<>();

        if (!adsIsFail(query)) {
            System.out.println("All page: " + getLastListPagination(query));

            for (int i = 1; i <= getLastListPagination(query); i++) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(URL_PATH + city + "/q-" + query + page + i).timeout(10000).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert doc != null;
                Elements elements = doc.getElementsByAttributeValue("class", "wrap");


                elements.forEach(element -> {
                    Element element1 = element.child(0);
                    String title = element1.select("h3 a").text();
                    String link = element.select("a[href]").attr("href");
                    String price = element1.select("p.price").text();
                    String date = element.select(".bottom-cell").text();

                    adsOLXList.add(new Ads(title, link, price, date));
                    countAds++;
                });
                System.out.println(doc.baseUri());

                for (Ads ads : adsOLXList) {
                    System.out.println(ads.toStringOLX());
                }

                System.out.println("All page: " + "-> " + getLastListPagination(query));
                System.out.println("Current page: " + "-> " + i);
                System.out.println("Total ADS: " + countAds);
            }
        }
        return adsOLXList;
    }

	/**
     * @return count pagination on page
     */
    @Override
    public int getLastListPagination(String query) {
        Document doc = null;
        try {
            doc = Jsoup.connect(URL_PATH + city + "/q-" + query).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
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
    public boolean adsIsFail(String query) {
        Document document = null;
        try {
            document = Jsoup.connect(URL_PATH + city.toString().toLowerCase() + "/q-" + query).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert document != null;
        Elements el = document.getElementsByAttributeValue("class", "c41 lheight24");
        if (el.hasClass("lheight24")) {
            System.out.println("No such messages, pls check query");
            return true;
        }
        return false;
    }
}
