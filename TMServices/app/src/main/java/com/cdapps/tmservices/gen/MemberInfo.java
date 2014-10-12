package com.cdapps.tmservices.gen;

/**
 * Created by dave on 11/10/14.
 */
public class MemberInfo {

    private String firstName;
    private String occupation;
    private String biography;
    private String quote;
    private Member member;

    public MemberInfo (String firstName, String occupation, String biography, String quote,
                       Member member)
    {
        this.firstName = firstName;
        this.occupation = occupation;
        this.biography = biography;
        this.quote = quote;
        this.member = member;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getBiography() {
        return biography;
    }

    public String getQuote() {
        return quote;
    }

    public Member getMember() {
        return member;
    }
}
