package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.UserEntity;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.UserReqUpdate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserService {
    UserEntity saveUser(UserEntity param);
    UserEntity getUserByEmail(String email);
    UserEntity updateUserPassword(UserReqUpdate param);
}
