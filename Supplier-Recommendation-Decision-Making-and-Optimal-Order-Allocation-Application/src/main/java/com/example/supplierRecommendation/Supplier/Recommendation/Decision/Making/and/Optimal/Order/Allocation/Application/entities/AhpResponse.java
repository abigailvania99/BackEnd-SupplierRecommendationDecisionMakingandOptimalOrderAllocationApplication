package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities;

public class AhpResponse {
    private double[][] perbandinganBerpasangan;
    private double[][] normalisasi;
    private double[] bobotPrioritas;
    private double[][] perbandinganBerpasanganDikaliBobot;
    private double[][] pembagianDenganBobot;
    private double lambdaX;
    private double CI;
    private double CR;

    public double[][] getPerbandinganBerpasangan() {
        return perbandinganBerpasangan;
    }

    public void setPerbandinganBerpasangan(double[][] perbandinganBerpasangan) {
        this.perbandinganBerpasangan = perbandinganBerpasangan;
    }

    public double[][] getNormalisasi() {
        return normalisasi;
    }

    public void setNormalisasi(double[][] normalisasi) {
        this.normalisasi = normalisasi;
    }

    public double[] getBobotPrioritas() {
        return bobotPrioritas;
    }

    public void setBobotPrioritas(double[] bobotPrioritas) {
        this.bobotPrioritas = bobotPrioritas;
    }

    public double[][] getPerbandinganBerpasanganDikaliBobot() {
        return perbandinganBerpasanganDikaliBobot;
    }

    public void setPerbandinganBerpasanganDikaliBobot(double[][] perbandinganBerpasanganDikaliBobot) {
        this.perbandinganBerpasanganDikaliBobot = perbandinganBerpasanganDikaliBobot;
    }

    public double[][] getPembagianDenganBobot() {
        return pembagianDenganBobot;
    }

    public void setPembagianDenganBobot(double[][] pembagianDenganBobot) {
        this.pembagianDenganBobot = pembagianDenganBobot;
    }

    public double getLambdaX() {
        return lambdaX;
    }

    public void setLambdaX(double lambdaX) {
        this.lambdaX = lambdaX;
    }

    public double getCI() {
        return CI;
    }

    public void setCI(double CI) {
        this.CI = CI;
    }

    public double getCR() {
        return CR;
    }

    public void setCR(double CR) {
        this.CR = CR;
    }

    public AhpResponse(double[][] perbandinganBerpasangan, double[][] normalisasi, double[] bobotPrioritas, double[][] perbandinganBerpasanganDikaliBobot, double[][] pembagianDenganBobot, double lambdaX, double CI, double CR) {
        this.perbandinganBerpasangan = perbandinganBerpasangan;
        this.normalisasi = normalisasi;
        this.bobotPrioritas = bobotPrioritas;
        this.perbandinganBerpasanganDikaliBobot = perbandinganBerpasanganDikaliBobot;
        this.pembagianDenganBobot = pembagianDenganBobot;
        this.lambdaX = lambdaX;
        this.CI = CI;
        this.CR = CR;
    }
}
