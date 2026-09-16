package com.expensemanager.service;

import com.expensemanager.exception.InvalidTransactionException;
import com.expensemanager.model.Category;
import com.expensemanager.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private final List<Transaction> transactions;
    private int nextId = 1;

    public ExpenseService(List<Transaction> initialData) {
        this.transactions = new ArrayList<>(initialData);
        for (Transaction t : initialData) {
            if (t.getId() >= nextId) {
                nextId = t.getId() + 1;
            }
        }
    }

    public synchronized void addTransaction(String desc, double amount, Category category, boolean isExpense) 
            throws InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException("Transaction amount must be strictly greater than zero.");
        }
        if (desc == null || desc.trim().isEmpty()) {
            throw new InvalidTransactionException("Description cannot be left blank.");
        }

        Transaction t = new Transaction(nextId++, desc, amount, category, isExpense, LocalDate.now());
        transactions.add(t);
        System.out.println("Transaction added successfully!");
    }

    public synchronized boolean deleteTransaction(int id) {
        return transactions.removeIf(t -> t.getId() == id);
    }

    public synchronized List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
}
