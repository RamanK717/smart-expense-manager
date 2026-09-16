package com.expensemanager.model;

import java.time.LocalDate;

public class Transaction {
    private final int id;
    private final String description;
    private final double amount;
    private final Category category;
    private final boolean isExpense; // true = Expense, false = Income
    private final LocalDate date;

    public Transaction(int id, String description, double amount, Category category, boolean isExpense, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.isExpense = isExpense;
        this.date = date;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Category getCategory() { return category; }
    public boolean isExpense() { return isExpense; }
    public LocalDate getDate() { return date; }

    public String toFileString() {
        return id + "," + description + "," + amount + "," + category.name() + "," + isExpense + "," + date;
    }

    public static Transaction fromFileString(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String desc = parts[1];
        double amt = Double.parseDouble(parts[2]);
        Category cat = Category.valueOf(parts[3]);
        boolean exp = Boolean.parseBoolean(parts[4]);
        LocalDate dt = LocalDate.parse(parts[5]);
        return new Transaction(id, desc, amt, cat, exp, dt);
    }

    @Override
    public String toString() {
        String type = isExpense ? "EXPENSE" : "INCOME ";
        return String.format("[%d] %s | %-12s | %-15s | $%.2f | %s",
                id, date, type, category.getDisplayName(), amount, description);
    }
}
