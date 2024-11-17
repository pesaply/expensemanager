package com.ict.client.services;

import com.ict.client.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ict.client.models.*;
import com.ict.client.repositories.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class AdminService {

    /**
     * This is the service which handles all the requests of the admins
     */

    private final UserModelRepository userModelRepository;
    private final LoginModelRepository loginModelRepository;
    private final ExpenseModelRepository expenseModelRepository;

    @Autowired
    public AdminService(UserModelRepository userModelRepository, LoginModelRepository loginModelRepository, ExpenseModelRepository expenseModelRepository) {
        this.userModelRepository = userModelRepository;
        this.loginModelRepository = loginModelRepository;
        this.expenseModelRepository = expenseModelRepository;
    }

    public Optional<UserModel> getAdminById(String admin_id) {
        return userModelRepository.findById(admin_id);
    }

    public ResponseModelListPayload<UserModel> getAllUsers() {

        return new ResponseModelListPayload<UserModel>(ResponseModel.SUCCESS, userModelRepository.findAll());
    }

    public ResponseModelSinglePayload<UserReceiverWithId> getUser(String userId) {
        Optional<UserModel> userById = userModelRepository.findById(userId);
        if (userById.isEmpty())
            return new ResponseModelSinglePayload<UserReceiverWithId>(ResponseModel.FAILURE, "User not found.", null);

        UserModel userModel=userById.get();
        LoginModel loginModel=loginModelRepository.findByEmail(userModel.getEmail()).get();
        UserReceiverWithId userReceiverWithId=new UserReceiverWithId(
                userModel.getUserId(),
                userModel.getEmail(),
                userModel.getUsername(),
                userModel.getMobileNumber(),
                userModel.isActive(),
                userModel.getRole(),
                loginModel.getPassword()
        );

        return new ResponseModelSinglePayload<UserReceiverWithId>(ResponseModel.SUCCESS,userReceiverWithId);
    }

    public ResponseModel deleteUserById(String userId) {
        Optional<UserModel> userById = userModelRepository.findById(userId);
        if (userById.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "User not found.");
        expenseModelRepository.deleteAllByUserId(userById.get());
        loginModelRepository.deleteByEmail(userById.get().getEmail());
        userModelRepository.delete(userById.get());
        return new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");
    }

    @Transactional
    public ResponseModelSinglePayload<UserModel> updateUser(
            String userId,
            UserReceiver userModelToUpdate
    ) {

        Optional<UserModel> userById = userModelRepository.findById(userId);

        if (userById.isEmpty())
            return new ResponseModelSinglePayload<UserModel>(ResponseModel.FAILURE, "User not found.", null);

        UserModel userModelCurrent = userById.get();
        Optional<LoginModel> loginByEmail = loginModelRepository.findByEmail(userModelCurrent.getEmail());
        LoginModel loginDetails = loginByEmail.get();

        if (userModelToUpdate.getEmail() != null && !Objects.equals(userModelCurrent.getEmail(), userModelToUpdate.getEmail())) {
            Optional<UserModel> emailExists = userModelRepository.findUserByEmail(userModelToUpdate.getEmail());
            if (emailExists.isPresent())
                return new ResponseModelSinglePayload<UserModel>(ResponseModel.EMAIL_TAKEN, "This email has already been taken.", null);
            loginDetails.setEmail(userModelToUpdate.getEmail());
            userModelCurrent.setEmail(userModelToUpdate.getEmail());
        }
        if (userModelToUpdate.getUsername() != null && !Objects.equals(userModelCurrent.getUsername(), userModelToUpdate.getUsername())) {
            userModelCurrent.setUsername(userModelToUpdate.getUsername());
        }
        if (userModelToUpdate.getMobileNumber() != null && !Objects.equals(userModelCurrent.getMobileNumber(), userModelToUpdate.getMobileNumber())) {
            userModelCurrent.setMobileNumber(userModelToUpdate.getMobileNumber());
        }
        if (!Objects.equals(userModelCurrent.isActive(), userModelToUpdate.isActive())) {
            userModelCurrent.setActive(userModelToUpdate.isActive());
        }
        if (userModelToUpdate.getRole() != null && !Objects.equals(userModelCurrent.getRole(), userModelToUpdate.getRole())) {
            userModelCurrent.setRole(userModelToUpdate.getRole());
        }

        // Return Successful with the updated user
        return new ResponseModelSinglePayload<UserModel>(
                ResponseModel.SUCCESS, "User Updated Successfully",
                userModelCurrent
        );

    }

}
