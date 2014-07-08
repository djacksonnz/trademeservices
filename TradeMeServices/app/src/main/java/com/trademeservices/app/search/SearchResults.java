/**
 * SearchResults.java
 * 
 * Created 4/06/2014 David Jackson 
 */

package com.trademeservices.app.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Jackson
 * 
 * Class to hold search result information
 *
 */
public class SearchResults {

	/**
	 * 
	 */
	
	private int total;
	private int page;
	private int pageSize;
	private List<Results> results = new ArrayList<Results>();
	
	public SearchResults(int total, int page, int pageSize) {
		this.total = total;
		this.page = page;
		this.pageSize = pageSize;
	}

	/**
	 * @return the results
	 */
	public List<Results> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<Results> results) {
		this.results = results;
	}
	
	/**
	 * @param add single result to results list
	 */
	public void addResult(Results result)
	{
		this.results.add(result);
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

}
