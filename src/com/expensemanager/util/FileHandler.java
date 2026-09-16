package com.expensemanager.util;

import com.expensemanager.model.Transaction;
import com.expensemanager.service.Storable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler implements Storable<Transaction> {

    @Override
    public void saveData(List<Transaction> transactions, String filepath) {
        File file = new File(filepath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Transaction t : transactions) {
                writer.write(t.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving transaction data: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> loadData(String filepath) {
        List<Transaction> list = new ArrayList<>();
        File file = new File(filepath);

        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(Transaction.fromFileString(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transaction data: " + e.getMessage());
        }
        return list;
    }
}
