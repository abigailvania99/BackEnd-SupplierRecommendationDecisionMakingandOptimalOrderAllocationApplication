package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.SupplierEntity;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public SupplierEntity saveSupplier(SupplierEntity param) {
        return supplierRepository.save(param);
    }

    @Override
    public void deleteSupplier(int id) {

        supplierRepository.deleteById(id);
    }

    @Override
    public SupplierEntity getSupplierById(int id) {

        return supplierRepository.findById(id).get();
    }

    @Override
    public SupplierEntity updateSupplier(SupplierEntity param) {
        SupplierEntity findSupplier = supplierRepository.findById(param.getId()).get();
        findSupplier.setAddress(param.getAddress());
        findSupplier.setName(param.getName());
        findSupplier.setPhone(param.getPhone());

        return supplierRepository.save(findSupplier);
    }

    @Override
    public List<SupplierEntity> getSupplierByUserId(int userId) {
        return supplierRepository.findByUserId(userId);
    }
}
