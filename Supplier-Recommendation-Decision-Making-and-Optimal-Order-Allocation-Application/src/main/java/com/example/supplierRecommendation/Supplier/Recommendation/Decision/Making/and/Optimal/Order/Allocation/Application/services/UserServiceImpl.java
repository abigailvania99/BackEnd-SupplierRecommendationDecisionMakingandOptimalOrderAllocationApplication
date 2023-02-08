package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.UserEntity;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.UserReqUpdate;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserEntity saveUser(UserEntity param) {
        return userRepository.save(param);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    @Override
    public UserEntity updateUserPassword(UserReqUpdate param) {
        UserEntity userFindById = userRepository.findById(param.getId()).get();
        userFindById.setPassword(param.getPassword());

        return userRepository.save(userFindById);

    }
}
