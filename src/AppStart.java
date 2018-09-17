import service.CrawlerOlx;
import service.CrawlerPremier;

public class AppStart {


    public static void main(String[] args) {
        String query = "продам";

        System.out.println("---------------------------------OLX is Begin Parse---------------------------------");

        CrawlerOlx crawlerOlx = new CrawlerOlx();
        crawlerOlx.parsePage(query);

        System.out.println("---------------------------------OLX was Finished---------------------------------");

        System.out.println("---------------------------------Premier is Begin Parse---------------------------------");

        CrawlerPremier crawlerPremier = new CrawlerPremier();
        crawlerPremier.parsePage(query);

        System.out.println("---------------------------------Premier was Finished---------------------------------");
    }
}