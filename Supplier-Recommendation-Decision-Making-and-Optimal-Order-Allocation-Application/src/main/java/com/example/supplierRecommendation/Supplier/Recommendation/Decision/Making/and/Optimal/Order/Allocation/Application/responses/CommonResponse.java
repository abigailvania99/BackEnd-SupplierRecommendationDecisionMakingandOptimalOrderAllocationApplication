package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses;

public class CommonResponse<T> {
    private String status;
    private String message;
    private T datas;

    public CommonResponse(){

    }

    public CommonResponse(String status, String message, T datas) {
        this.status = status;
        this.message = message;
        this.datas = datas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }
}
