package entity;

import controller.CrawlerPremier;

public class AdsPremier {
    private String title;
    private String link;
    private String price;
    private String date;

    public AdsPremier(String title, String link, String price, String date) {
        this.title = title;
        this.link = link;
        this.price = price;
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return
                "- title = " + title + "\n" +
                        "- link = " + CrawlerPremier.getUrlPath() + link + "\n" +
                        "- date = " + date + "\n" +
                        "- price = " + price + "\n";
    }
}