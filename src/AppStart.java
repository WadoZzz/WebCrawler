import city.City;
import service.CrawlerOlx;
import service.CrawlerPremier;

public class AppStart {

    public static void main(String[] args) {
        String query = "Тест";

        CrawlerOlx crawlerOlx = new CrawlerOlx();
        crawlerOlx.parsePage(query, City.KHMELNITSKIY);

        CrawlerPremier crawlerPremier = new CrawlerPremier();
        crawlerPremier.parsePage(query);
    }
}