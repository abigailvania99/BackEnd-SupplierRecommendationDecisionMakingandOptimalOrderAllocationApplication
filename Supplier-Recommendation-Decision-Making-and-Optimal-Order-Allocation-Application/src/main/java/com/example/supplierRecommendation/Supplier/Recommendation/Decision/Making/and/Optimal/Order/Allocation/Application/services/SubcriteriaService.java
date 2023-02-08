package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.SubcriteriaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SubcriteriaService {
    SubcriteriaEntity saveSubcriteria(SubcriteriaEntity param);

    void deleteSubcriteria(int id);

    SubcriteriaEntity getSubcriteriaById(int id);

    SubcriteriaEntity updateSubcriteria(SubcriteriaEntity param);

    List<SubcriteriaEntity> getSubcriteriaByUserId(int userId);
}
