package com.expensemanager.model;

public enum Category {
    FOOD("Food & Dining"),
    TRANSPORT("Transportation"),
    ENTERTAINMENT("Entertainment"),
    UTILITIES("Utilities & Bills"),
    SALARY("Salary & Income"),
    OTHER("Miscellaneous");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}