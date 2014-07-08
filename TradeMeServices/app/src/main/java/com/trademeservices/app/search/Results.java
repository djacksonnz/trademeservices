/**
 * Results.java
 * 
 * Created 4/06/2014 David Jackson 
 */

package com.trademeservices.app.search;

/**
 * @author David Jackson
 *
 * Class to hold result item of search
 */
public class Results {

	/**
	 * 
	 */
	
	private int id;
	private String title;
	private String cat;
	private String pic;
	
	public Results(int id, String title, String cat, String pic) {
		this.id = id;
		this.title = title;
		this.cat = cat;
		this.pic = pic;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the cat
	 */
	public String getCat() {
		return cat;
	}

	/**
	 * @return the pic
	 */
	public String getPic() {
		return pic;
	}

}
