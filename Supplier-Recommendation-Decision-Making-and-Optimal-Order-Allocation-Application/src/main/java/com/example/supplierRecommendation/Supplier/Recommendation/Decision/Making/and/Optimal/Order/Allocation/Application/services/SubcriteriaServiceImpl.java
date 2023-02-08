package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.SubcriteriaEntity;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.repositories.SubcriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcriteriaServiceImpl implements SubcriteriaService{
    @Autowired
    SubcriteriaRepository subcriteriaRepository;

    @Override
    public SubcriteriaEntity saveSubcriteria(SubcriteriaEntity param) {

        return subcriteriaRepository.save(param);
    }

    @Override
    public void deleteSubcriteria(int id) {
        subcriteriaRepository.deleteById(id);
    }

    @Override
    public SubcriteriaEntity getSubcriteriaById(int id) {

        return subcriteriaRepository.findById(id).get();
    }

    @Override
    public SubcriteriaEntity updateSubcriteria(SubcriteriaEntity param) {
        SubcriteriaEntity findSubcriteria = subcriteriaRepository.findById(param.getId()).get();
        findSubcriteria.setSubcriteria(param.getSubcriteria());
        findSubcriteria.setCriteria(param.getCriteria());
        findSubcriteria.setType(param.getType());

        return subcriteriaRepository.save(findSubcriteria);
    }

    @Override
    public List<SubcriteriaEntity> getSubcriteriaByUserId(int userId) {
        return subcriteriaRepository.findByUserId(userId);
    }
}
