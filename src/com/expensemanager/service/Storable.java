package com.expensemanager.service;

import java.util.List;

public interface Storable<T> {
    void saveData(List<T> data, String filepath);
    List<T> loadData(String filepath);
}