package com.got.model;

public class FinancialRecord {
    private String name;
    private String date;
    private String description;
    private double amount; // + -> debt; - -> balance

    // Provide for RowMapper
    public FinancialRecord() {}

    public FinancialRecord(String name, String date, String description, double amount) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    // Getters
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDate(String date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
}