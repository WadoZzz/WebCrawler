package service;

public interface ICrawler {

    /**
     * Find elements on current the page
     */
    void ParsePage();

    /**
     *
     * get count pagination on page, and @return last cont page
      */
    int getLastListPageination();

    /**
     *
     * checks if there was a correct request
     */
    boolean ADSisFail();
}
