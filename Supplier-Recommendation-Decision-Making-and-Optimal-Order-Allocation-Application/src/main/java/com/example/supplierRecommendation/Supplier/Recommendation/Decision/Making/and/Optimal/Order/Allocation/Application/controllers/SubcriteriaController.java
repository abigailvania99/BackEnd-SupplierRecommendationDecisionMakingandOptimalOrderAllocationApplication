package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.controllers;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.SubcriteriaEntity;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses.CommonResponse;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses.CommonResponseGenerator;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services.SubcriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "subcriteria")

public class SubcriteriaController {

    @Autowired
    SubcriteriaService subcriteriaService;

    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @PostMapping(value = "addSubcriteria")
    public CommonResponse<SubcriteriaEntity> addNewSubcriteria(@RequestBody SubcriteriaEntity param){
        try {
            SubcriteriaEntity subcriteria = subcriteriaService.saveSubcriteria(param);
            return commonResponseGenerator.successResponse(subcriteria, "Success Add New Subcriteria");
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @DeleteMapping(value = "deleteSubcriteria")
    public CommonResponse<SubcriteriaEntity> deleteSubcriteria(@RequestParam int id) {
        try {
            SubcriteriaEntity deleteSubcriteria = subcriteriaService.getSubcriteriaById(id);
            subcriteriaService.deleteSubcriteria(id);
            return commonResponseGenerator.successResponse(deleteSubcriteria, "Success Delete Subcriteria id: " + id);
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping(value = "updateSubcriteria")
    public CommonResponse<SubcriteriaEntity> updateSubcriteria(@RequestBody SubcriteriaEntity param) {
        try {
            SubcriteriaEntity subcriteria = subcriteriaService.updateSubcriteria(param);
            return commonResponseGenerator.successResponse(subcriteria, "Success Update Subcriteria id: " + subcriteria.getId());
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage()+" for id: "+param.getId());
        }
    }

    @GetMapping(value="getAllSubcriteria")
    public CommonResponse<List<SubcriteriaEntity>> getAllSubcriteria(@RequestParam int id){
        try {
            List<SubcriteriaEntity> subcriteriaList = subcriteriaService.getSubcriteriaByUserId(id);
            return commonResponseGenerator.successResponse(subcriteriaList, "Success Get All Subcriteria");
        } catch (Exception e){
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

}
