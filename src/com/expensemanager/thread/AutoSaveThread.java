package com.expensemanager.thread;

import com.expensemanager.model.Transaction;
import com.expensemanager.service.ExpenseService;
import com.expensemanager.service.Storable;

import java.util.List;

public class AutoSaveThread extends Thread {
    private final ExpenseService expenseService;
    private final Storable<Transaction> fileHandler;
    private final String filePath;
    private boolean running = true;

    public AutoSaveThread(ExpenseService expenseService, Storable<Transaction> fileHandler, String filePath) {
        this.expenseService = expenseService;
        this.fileHandler = fileHandler;
        this.filePath = filePath;
        // Allows JVM to exit cleanly if main loop exits
        this.setDaemon(true); 
    }

    public void stopAutoSave() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Background autosave triggers every 15 seconds
                Thread.sleep(15000); 
                List<Transaction> currentData = expenseService.getAllTransactions();
                fileHandler.saveData(currentData, filePath);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}