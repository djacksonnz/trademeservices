package com.trademeservices.app.reviews;

import com.trademeservices.app.gen.Member;

import java.util.Date;

/**
 * Created by jacksdl2 on 7/08/2014.
 */
public class Review {
    private int reviewId;
    private Date date;
    private boolean positive;
    private String reviewText;
    private Member member;
    private String response;
    private Date responseDate;

    public Review(int reviewId, Date date, boolean positive, String reviewText, Member member,
                  String response, Date responseDate)
    {
        this.reviewId = reviewId;
        this.date = date;
        this.positive = positive;
        this.reviewText = reviewText;
        this.member = member;
        this.response = response;
        this.responseDate = responseDate;
    }


    public int getReviewId() {
        return reviewId;
    }

    public Date getDate() {
        return date;
    }

    public boolean isPositive() {
        return positive;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Member getMember() {
        return member;
    }

    public String getResponse() {
        return response;
    }

    public Date getResponseDate() {
        return responseDate;
    }
}
