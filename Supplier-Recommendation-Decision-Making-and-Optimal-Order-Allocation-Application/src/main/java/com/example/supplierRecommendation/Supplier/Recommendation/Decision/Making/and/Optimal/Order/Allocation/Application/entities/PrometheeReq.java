package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities;

public class PrometheeReq {
    private double[] matrix;
    private String[] type;
    private double[] bobot;
    private int lengthSupplier;

    public int getLengthSupplier() {
        return lengthSupplier;
    }

    public void setLengthSupplier(int lengthSupplier) {
        this.lengthSupplier = lengthSupplier;
    }

    public double[] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[] matrix) {
        this.matrix = matrix;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public double[] getBobot() {
        return bobot;
    }

    public void setBobot(double[] bobot) {
        this.bobot = bobot;
    }
}
