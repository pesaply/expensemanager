package com.ict.client.controllers;

import com.ict.client.models.LoginModel;
import com.ict.client.models.ResponseModel;
import com.ict.client.models.ResponseModelSinglePayload;
import com.ict.client.models.UserModel;
import com.ict.client.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "login")
public class LoginController {

    /**
     * This Controller class provides the endpoint for Login
     */

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseModelSinglePayload<UserModel> checkUser(@RequestBody LoginModel loginModel)
    {
        return loginService.checkUser(loginModel);
    }

}
