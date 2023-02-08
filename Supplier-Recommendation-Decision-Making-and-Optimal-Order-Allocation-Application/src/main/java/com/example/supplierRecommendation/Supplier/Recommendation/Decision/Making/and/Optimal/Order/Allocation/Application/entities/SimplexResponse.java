package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities;

public class SimplexResponse {
    private double[][] result;

    public double[][] getResult() {
        return result;
    }

    public void setResult(double[][] result) {
        this.result = result;
    }

    public SimplexResponse(double[][] result) {
        this.result = result;
    }
}
