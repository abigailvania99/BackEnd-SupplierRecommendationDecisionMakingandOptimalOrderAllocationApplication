package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.controllers;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.SupplierEntity;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses.CommonResponse;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses.CommonResponseGenerator;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @PostMapping(value = "addSupplier")
    public CommonResponse<SupplierEntity> addNewSupplier(@RequestBody SupplierEntity param){
        try {
            SupplierEntity supplier = supplierService.saveSupplier(param);
            return commonResponseGenerator.successResponse(supplier, "Success Add New Supplier");
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @DeleteMapping(value = "deleteSupplier")
    public CommonResponse<SupplierEntity> deleteSupplier(@RequestParam int id) {
        try {
            SupplierEntity deleteSupplier = supplierService.getSupplierById(id);
            supplierService.deleteSupplier(id);
            return commonResponseGenerator.successResponse(deleteSupplier, "Success Delete Supplier id: " + id);
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping(value = "updateSupplier")
    public CommonResponse<SupplierEntity> updateSupplier(@RequestBody SupplierEntity param) {
        try {
            SupplierEntity supplier = supplierService.updateSupplier(param);
            return commonResponseGenerator.successResponse(supplier, "Success Update Supplier id: " + supplier.getId());
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage()+" for id: "+param.getId());
        }
    }

    @GetMapping(value="getAllSupplier")
    public CommonResponse<List<SupplierEntity>> getAllSupplier(@RequestParam int id){
        try {
            List<SupplierEntity> supplierList = supplierService.getSupplierByUserId(id);
            return commonResponseGenerator.successResponse(supplierList, "Success Get All Supplier");
        } catch (Exception e){
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }


}
