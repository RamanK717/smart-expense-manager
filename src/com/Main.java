package com.expensemanager;

import com.expensemanager.exception.InvalidTransactionException;
import com.expensemanager.model.Category;
import com.expensemanager.model.Transaction;
import com.expensemanager.model.User;
import com.expensemanager.service.AnalyticsService;
import com.expensemanager.service.ExpenseService;
import com.expensemanager.thread.AutoSaveThread;
import com.expensemanager.util.FileHandler;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "data/transactions.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileHandler fileHandler = new FileHandler();

        System.out.println("=========================================");
        System.out.println("   SMART EXPENSE & BUDGET MANAGER       ");
        System.out.println("=========================================");

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        User user = new User(username, email);
        System.out.println("\nWelcome, " + user.getUsername() + "!");

        // Load persisted data from disk
        List<Transaction> existingData = fileHandler.loadData(DATA_FILE);
        ExpenseService service = new ExpenseService(existingData);

        // Background Daemon Thread for Auto-Saving Data
        AutoSaveThread autoSaveThread = new AutoSaveThread(service, fileHandler, DATA_FILE);
        autoSaveThread.start();

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View All Transactions");
            System.out.println("4. Delete Transaction");
            System.out.println("5. View Financial Analytics");
            System.out.println("6. Save & Exit");
            System.out.print("Choose an option (1-6): ");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    handleAdd(scanner, service, false);
                    break;
                case "2":
                    handleAdd(scanner, service, true);
                    break;
                case "3":
                    handleViewAll(service);
                    break;
                case "4":
                    handleDelete(scanner, service);
                    break;
                case "5":
                    AnalyticsService.displaySummary(service.getAllTransactions());
                    break;
                case "6":
                    fileHandler.saveData(service.getAllTransactions(), DATA_FILE);
                    autoSaveThread.stopAutoSave();
                    System.out.println("Data saved successfully. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection! Please enter a number between 1 and 6.");
            }
        }
        scanner.close();
    }

    private static void handleAdd(Scanner scanner, ExpenseService service, boolean isExpense) {
        try {
            System.out.print("Enter Description: ");
            String desc = scanner.nextLine();

            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.println("Select Category:");
            Category[] categories = Category.values();
            for (int i = 0; i < categories.length; i++) {
                System.out.printf("  %d. %s\n", i + 1, categories[i].getDisplayName());
            }
            System.out.print("Category choice #: ");
            int catIdx = Integer.parseInt(scanner.nextLine()) - 1;

            if (catIdx < 0 || catIdx >= categories.length) {
                System.out.println("Invalid category selection. Transaction aborted.");
                return;
            }

            service.addTransaction(desc, amount, categories[catIdx], isExpense);
        } catch (NumberFormatException e) {
            System.out.println("Error: Amount and Category must be valid numeric values.");
        } catch (InvalidTransactionException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        }
    }

    private static void handleViewAll(ExpenseService service) {
        List<Transaction> list = service.getAllTransactions();
        System.out.println("\n-------------------------------- TRANSACTION LIST --------------------------------");
        if (list.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (Transaction t : list) {
                System.out.println(t);
            }
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    private static void handleDelete(Scanner scanner, ExpenseService service) {
        try {
            System.out.print("Enter Transaction ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean deleted = service.deleteTransaction(id);
            if (deleted) {
                System.out.println("Transaction #" + id + " deleted successfully.");
            } else {
                System.out.println("No transaction found with ID #" + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please provide a valid numerical ID.");
        }
    }
}
