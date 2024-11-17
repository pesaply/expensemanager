package com.ict.client.controllers;

import com.ict.client.services.AdminService;
import com.ict.client.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ict.client.models.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    /**
     * This Controller class gives the endpoints for the admins
     */

    private final AdminService adminService;
    private final SignupService signupService;

    @Autowired
    public AdminController(AdminService adminService, SignupService signupService) {
        this.adminService = adminService;
        this.signupService = signupService;
    }

    @GetMapping
    public ResponseModelListPayload<UserModel> getAllUsers(@RequestHeader("adminId") String adminId)
    {
        // Check Admin Validity
        Optional<UserModel> adminById = adminService.getAdminById(adminId);
        if(adminById.isEmpty())
            return new ResponseModelListPayload<>(ResponseModel.FAILURE, "Admin not found.", null);

        // Check Role validity
        if(!adminById.get().getRole().equals(UserModel.ADMIN))
            return new ResponseModelListPayload<>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please Login again.", null);

        if(!adminById.get().isActive())
            return new ResponseModelListPayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);
        return adminService.getAllUsers();
    }

    @GetMapping(path = "{user_id}")
    public ResponseModelSinglePayload<UserReceiverWithId> getUser(@PathVariable("user_id") String userId, @RequestHeader("adminId") String adminId)
    {
        // Check admin validity
        Optional<UserModel> adminById = adminService.getAdminById(adminId);
        if(adminById.isEmpty())
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Admin not found.", null);

        // Check role validity
        if(!adminById.get().getRole().equals(UserModel.ADMIN))
            return new ResponseModelSinglePayload<>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please Login again.", null);

        if(!adminById.get().isActive())
            return new ResponseModelSinglePayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);
        return adminService.getUser(userId);
    }

    @PutMapping(path = "user/{user_id}")
    public ResponseModelSinglePayload<UserModel> updateUser(@PathVariable("user_id") String userId, @RequestBody UserReceiver userReceiver, @RequestHeader("adminId") String adminId)
    {
        // Check admin validity
        Optional<UserModel> adminById = adminService.getAdminById(adminId);
        if(adminById.isEmpty())
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Admin not found.", null);

        // Check role validity
        if(!adminById.get().getRole().equals(UserModel.ADMIN))
            return new ResponseModelSinglePayload<>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please Login again.", null);

        if(!adminById.get().isActive())
            return new ResponseModelSinglePayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);
        return adminService.updateUser(userId, userReceiver);
    }

    @DeleteMapping(path = "user/{user_id}")
    public ResponseModel deleteUser(@PathVariable("user_id") String userId, @RequestHeader("adminId") String adminId)
    {
        // Check admin validity
        Optional<UserModel> adminById = adminService.getAdminById(adminId);
        if(adminById.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Admin not found.");

        // Check role validity
        if(!adminById.get().getRole().equals(UserModel.ADMIN))
            return new ResponseModel(ResponseModel.ROLE_CHANGED, "Your role has changed. Please Login again.");

        if(!adminById.get().isActive())
            return new ResponseModel(ResponseModel.INACTIVE, "Your account is currently suspended.");
        return adminService.deleteUserById(userId);
    }

    @PostMapping("user")
    public ResponseModelSinglePayload<UserModel> addUser(@RequestBody UserReceiver userReceiver, @RequestHeader("adminId") String adminId)
    {
        // Check admin validity
        Optional<UserModel> adminById = adminService.getAdminById(adminId);
        if(adminById.isEmpty())
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Admin not found.",null);

        // Check role validity
        if(!adminById.get().getRole().equals(UserModel.ADMIN))
            return new ResponseModelSinglePayload<>(ResponseModel.ROLE_CHANGED, "Your role has changed. Please Login again.", null);

        if(!adminById.get().isActive())
            return new ResponseModelSinglePayload<>(ResponseModel.INACTIVE, "Your account is currently suspended.", null);
        return signupService.saveUser(userReceiver);
    }
}
