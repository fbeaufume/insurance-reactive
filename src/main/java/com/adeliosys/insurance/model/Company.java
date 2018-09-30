package com.adeliosys.insurance.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

/**
 * An insurance company.
 */
@Document
public class Company {

    private static final List<Company> defaultCompanies = Arrays.asList(
            new Company("1", "Alpha Insurance", "Coverage of Alpha Insurance", 150),
            new Company("2", "Bravo Insurance", "Coverage of Bravo Insurance", 120),
            new Company("3", "Charlie Insurance", "Coverage of Charlie Insurance", 180),
            new Company("4", "Delta Insurance", "Coverage of Delta Insurance", 140),
            new Company("5", "Echo Insurance", "Coverage of Echo Insurance", 160));

    public static List<Company> getDefaultCompanies() {
        return defaultCompanies;
    }

    /**
     * Persistence ID of the entity.
     */
    private String id;

    /**
     * Name of the insurance company.
     */
    private String name;

    /**
     * Coverage of the simulated quotes.
     */
    private String coverage;

    /**
     * Cost of the simulated quotes.
     */
    private float cost;

    public Company() {
    }

    public Company(String id, String name, String coverage, float cost) {
        this.id = id;
        this.name = name;
        this.coverage = coverage;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoverage() {
        return coverage;
    }

    public float getCost() {
        return cost;
    }
}
