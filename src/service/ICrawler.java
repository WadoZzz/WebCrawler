package service;

public interface ICrawler {

    void ParsePage();

    int getLastListPageination();

    boolean ADSisFail();
}
