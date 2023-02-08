package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.*;
import org.springframework.stereotype.Component;

@Component
public interface AhpPrometheeSimplexService {
    AhpResponse countAhp(AhpReq param);
    PrometheeResponse countPromethee(PrometheeReq param);
    SimplexResponse countSimplex(SimplexReq param);

}
