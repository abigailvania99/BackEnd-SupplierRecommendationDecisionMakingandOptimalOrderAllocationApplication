package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities;

public class PrometheeResponse {

    private double[][] nilaiAlternatif;
    private double[][] normalisasi;
    private double[][] selisih;
    private double[][] nilaiPreferensi;
    private double[][] nilaiIndeksPreferensiMultiKriteria;
    private double[][] aggregatPreferensi;
    private double[] enteringFlow;
    private double[] leavingFlow;
    private double[] netFlow;
    private int[] rank;

    public PrometheeResponse(double[][] nilaiAlternatif, double[][] normalisasi, double[][] selisih, double[][] nilaiPreferensi, double[][] nilaiIndeksPreferensiMultiKriteria, double[][] aggregatPreferensi, double[] enteringFlow, double[] leavingFlow, double[] netFlow, int[] rank) {
        this.nilaiAlternatif = nilaiAlternatif;
        this.normalisasi = normalisasi;
        this.selisih = selisih;
        this.nilaiPreferensi = nilaiPreferensi;
        this.nilaiIndeksPreferensiMultiKriteria = nilaiIndeksPreferensiMultiKriteria;
        this.aggregatPreferensi = aggregatPreferensi;
        this.enteringFlow = enteringFlow;
        this.leavingFlow = leavingFlow;
        this.netFlow = netFlow;
        this.rank = rank;
    }

    public double[][] getNilaiAlternatif() {
        return nilaiAlternatif;
    }

    public void setNilaiAlternatif(double[][] nilaiAlternatif) {
        this.nilaiAlternatif = nilaiAlternatif;
    }

    public double[][] getNormalisasi() {
        return normalisasi;
    }

    public void setNormalisasi(double[][] normalisasi) {
        this.normalisasi = normalisasi;
    }

    public double[][] getSelisih() {
        return selisih;
    }

    public void setSelisih(double[][] selisih) {
        this.selisih = selisih;
    }

    public double[][] getNilaiPreferensi() {
        return nilaiPreferensi;
    }

    public void setNilaiPreferensi(double[][] nilaiPreferensi) {
        this.nilaiPreferensi = nilaiPreferensi;
    }

    public double[][] getNilaiIndeksPreferensiMultiKriteria() {
        return nilaiIndeksPreferensiMultiKriteria;
    }

    public void setNilaiIndeksPreferensiMultiKriteria(double[][] nilaiIndeksPreferensiMultiKriteria) {
        this.nilaiIndeksPreferensiMultiKriteria = nilaiIndeksPreferensiMultiKriteria;
    }

    public double[][] getAggregatPreferensi() {
        return aggregatPreferensi;
    }

    public void setAggregatPreferensi(double[][] aggregatPreferensi) {
        this.aggregatPreferensi = aggregatPreferensi;
    }

    public double[] getEnteringFlow() {
        return enteringFlow;
    }

    public void setEnteringFlow(double[] enteringFlow) {
        this.enteringFlow = enteringFlow;
    }

    public double[] getLeavingFlow() {
        return leavingFlow;
    }

    public void setLeavingFlow(double[] leavingFlow) {
        this.leavingFlow = leavingFlow;
    }

    public double[] getNetFlow() {
        return netFlow;
    }

    public void setNetFlow(double[] netFlow) {
        this.netFlow = netFlow;
    }

    public int[] getRank() {
        return rank;
    }

    public void setRank(int[] rank) {
        this.rank = rank;
    }
}
