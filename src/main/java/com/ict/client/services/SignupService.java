package com.ict.client.services;

import com.ict.client.models.*;
import com.ict.client.repositories.LoginModelRepository;
import com.ict.client.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {

    /**
     * This Service class handles the requests of the Signup endpoint
     */

    private final UserModelRepository userModelRepository;
    private final LoginModelRepository loginModelRepository;

    @Autowired
    public SignupService(UserModelRepository userModelRepository, LoginModelRepository loginModelRepository) {
        this.userModelRepository = userModelRepository;
        this.loginModelRepository = loginModelRepository;
    }

    public ResponseModelSinglePayload<UserModel> saveUser(UserReceiver userReceiver) {

        // Check email validity
        Optional<UserModel> userByEmail = userModelRepository.findUserByEmail(userReceiver.getEmail());

        if(userByEmail.isPresent())
            return new ResponseModelSinglePayload<>(ResponseModel.EMAIL_TAKEN, "This email is already taken. Try another one.",null);
        // Save user if email is valid
        UserModel userModel=userReceiver.getUserModel();
        userModelRepository.save(userModel);
        loginModelRepository.save(new LoginModel(userReceiver.getEmail(), userReceiver.getPassword()));

        return new ResponseModelSinglePayload<UserModel>(ResponseModel.SUCCESS, "User Added Successfully", userModel);
    }
}
