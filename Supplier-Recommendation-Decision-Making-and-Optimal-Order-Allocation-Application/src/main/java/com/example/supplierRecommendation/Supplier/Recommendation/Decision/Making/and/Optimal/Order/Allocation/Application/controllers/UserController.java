package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.controllers;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.UserEntity;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.UserReqUpdate;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses.CommonResponse;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.responses.CommonResponseGenerator;
import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @PostMapping(value="addUser")
    public CommonResponse<UserEntity> addUser(@RequestBody UserEntity param){
        try {
            UserEntity checkUser = userService.getUserByEmail(param.getEmail());
            if(checkUser != null){
                return commonResponseGenerator.failedResponse("Registration with other email");
            }

            UserEntity user = userService.saveUser(param);
            return commonResponseGenerator.successResponse(user, "Success Add New User");
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @GetMapping(value="getUser")
    public CommonResponse<UserEntity> getUser(@RequestParam String email){
        try {
            UserEntity user = userService.getUserByEmail(email);
            if(user == null){
                return commonResponseGenerator.failedResponse("No data user");
            }
            return commonResponseGenerator.successResponse(user, "Success Get User email: " + email);
        } catch (Exception e) {
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
    @PutMapping(value="updatePassword")
    public CommonResponse<UserEntity> updatePassword(@RequestBody UserReqUpdate param){
        try{
            UserEntity user = userService.updateUserPassword(param);
                return commonResponseGenerator.successResponse(user, "Success Update Password user id: " + param.getId());

        } catch (Exception e){
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
}
