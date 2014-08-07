/**
 * SearchResults.java
 * 
 * Created 4/06/2014 David Jackson 
 */

package com.trademeservices.app.search;

import com.trademeservices.app.cat.Categories;

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
    private List<Categories> foundCategories;
    private int foundCategoriesNum;
	
	public SearchResults(int total, int page, int pageSize, List<Categories> foundCategories) {
		this.total = total;
		this.page = page;
		this.pageSize = pageSize;
        this.foundCategories = foundCategories;
        foundCategoriesNum = foundCategories.size();
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

    public void AddFoundCategories(Categories cat)
    {
        foundCategories.add(cat);
    }

    public List<Categories> getFoundCategories() {
        return foundCategories;
    }

    public void setFoundCategories(List<Categories> foundCategories) {
        this.foundCategories = foundCategories;
    }

    public int getFoundCategoriesNum() {
        return foundCategoriesNum;
    }
}
