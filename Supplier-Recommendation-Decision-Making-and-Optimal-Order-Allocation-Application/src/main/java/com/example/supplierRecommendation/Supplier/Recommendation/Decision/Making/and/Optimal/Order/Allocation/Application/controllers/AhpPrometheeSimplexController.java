package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.controllers;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.*;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses.CommonResponse;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses.CommonResponseGenerator;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services.AhpPrometheeSimplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "ahpprometheesimplex")
public class AhpPrometheeSimplexController {

    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @Autowired
    AhpPrometheeSimplexService ahpPrometheeSimplexService;

    @PostMapping(value="ahp")
    public CommonResponse<AhpResponse> countAhp (@RequestBody AhpReq param){
        try {
            AhpResponse ahpResponse = ahpPrometheeSimplexService.countAhp(param);
            return commonResponseGenerator.successResponse(ahpResponse, "Success Count AHP");
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PostMapping(value="promethee")
    public CommonResponse<PrometheeResponse> countPromethee(@RequestBody PrometheeReq param){
        try {
            PrometheeResponse prometheeResponse = ahpPrometheeSimplexService.countPromethee(param);
            return commonResponseGenerator.successResponse(prometheeResponse, "Success Count Promethee");
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PostMapping(value="simplex")
    public CommonResponse<SimplexResponse> countSimplex(@RequestBody SimplexReq param){
        try {
            SimplexResponse simplexResponse = ahpPrometheeSimplexService.countSimplex(param);
            return commonResponseGenerator.successResponse(simplexResponse, "Success Count Simplex");
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

}
