package com.expensemanager.service;

import com.expensemanager.model.Category;
import com.expensemanager.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsService {

    public static void displaySummary(List<Transaction> transactions) {
        double totalIncome = 0;
        double totalExpense = 0;
        Map<Category, Double> categoryBreakdown = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.isExpense()) {
                totalExpense += t.getAmount();
                categoryBreakdown.put(t.getCategory(), 
                    categoryBreakdown.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            } else {
                totalIncome += t.getAmount();
            }
        }

        double netSavings = totalIncome - totalExpense;

        System.out.println("\n========== FINANCIAL ANALYTICS ==========");
        System.out.printf("Total Income:   $%.2f\n", totalIncome);
        System.out.printf("Total Expense:  $%.2f\n", totalExpense);
        System.out.printf("Net Savings:    $%.2f\n", netSavings);
        System.out.println("-----------------------------------------");
        System.out.println("Expense Breakdown by Category:");
        
        if (categoryBreakdown.isEmpty()) {
            System.out.println("  No expenses recorded yet.");
        } else {
            for (Map.Entry<Category, Double> entry : categoryBreakdown.entrySet()) {
                System.out.printf("  - %-20s: $%.2f\n", entry.getKey().getDisplayName(), entry.getValue());
            }
        }
        System.out.println("=========================================\n");
    }
}