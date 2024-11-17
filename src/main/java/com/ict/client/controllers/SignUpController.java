package com.ict.client.controllers;

import com.ict.client.models.ResponseModel;
import com.ict.client.models.ResponseModelSinglePayload;
import com.ict.client.models.UserModel;
import com.ict.client.models.UserReceiver;
import com.ict.client.services.SignupService;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "signup")
public class SignUpController {

    /**
     * This Controller class provides the Sign up endpoint
     */

    private final SignupService signupService;

    @Autowired
    public SignUpController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping
    public ResponseModelSinglePayload<UserModel> saveUser(@RequestBody UserReceiver userReceiver)
    {
        return signupService.saveUser(userReceiver);
    }
}
