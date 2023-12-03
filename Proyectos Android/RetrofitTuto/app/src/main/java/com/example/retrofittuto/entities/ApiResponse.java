package com.example.retrofittuto.entities;

import java.util.List;

public class ApiResponse {
    private List<Card> data;

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }
}
