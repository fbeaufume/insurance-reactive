package com.adeliosys.insurance.model;

/**
 * An insurance quote.
 */
public class Quote implements Comparable<Quote> {

    private String companyName;

    private String coverage;

    private float cost;

    public Quote() {
    }

    public Quote(Company company) {
        if (company != null) {
            companyName = company.getName();
            coverage =  company.getCoverage();
            cost = company.getCost();
        }
    }

    public Quote(String companyName, String coverage, float cost) {
        this.companyName = companyName;
        this.coverage = coverage;
        this.cost = cost;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Quote o) {
        return Float.compare(cost, o.getCost());
    }
}
