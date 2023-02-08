package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.SupplierEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SupplierService {

    SupplierEntity saveSupplier(SupplierEntity param);

    void deleteSupplier(int id);

    SupplierEntity getSupplierById(int id);

    SupplierEntity updateSupplier(SupplierEntity param);

    List<SupplierEntity> getSupplierByUserId(int userId);
}
