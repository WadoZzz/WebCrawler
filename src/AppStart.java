import city.City;
import controller.CrawlerOLX;
import controller.CrawlerPremier;

public class AppStart {

    private static String query = "продам";

    public static void main(String[] args) {

//        System.out.println("---------------------------------OLX is Begin---------------------------------");
//
//        CrawlerOLX crawlerOLX = new CrawlerOLX(query, City.kiev);
//        crawlerOLX.ParsePage();
//
//        System.out.println("---------------------------------OLX was Finished---------------------------------");


        System.out.println("---------------------------------Premier is Begin---------------------------------");

        CrawlerPremier crawlerPremier = new CrawlerPremier(query);
        crawlerPremier.ParsePage();

        System.out.println("---------------------------------Premier was Finished---------------------------------");

    }
}
