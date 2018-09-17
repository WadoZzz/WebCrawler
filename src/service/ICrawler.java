package service;

import java.util.Set;

public interface ICrawler {

	/**
	 * Find elements on current the page
	 * 
	 * @return
	 */
	Set<?> parsePage(String query);

	/**
	 *
	 * get count pagination on page, and @return last cont page
	 */
	int getLastListPagination(String query);

	/**
	 *
	 * checks if there was a correct request
	 */
	boolean adsIsFail(String query);
}
