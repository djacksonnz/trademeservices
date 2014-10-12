package com.cdapps.tmservices.reviews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksdl2 on 7/08/2014.
 */
public class ReviewResults {
    private int totalCount;
    private int page;
    private int pageSize;
    private List<Review> reviewsList = new ArrayList<Review>();

    public ReviewResults(int totalCount, int page, int pageSize)
    {
        this.totalCount = totalCount;
        this.page = page;
        this.pageSize = pageSize;
    }


    public int getTotalCount() {
        return totalCount;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public void AddReview(Review review)
    {
        reviewsList.add(review);
    }
}
