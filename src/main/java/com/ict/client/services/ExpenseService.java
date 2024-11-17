package com.ict.client.services;

import com.ict.client.models.*;
import com.ict.client.repositories.ExpenseModelRepository;
import com.ict.client.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ExpenseService {
    private static final String JANUARY = "JANUARY",
            FEBRUARY = "FEBRUARY",
            MARCH = "MARCH",
            APRIL = "APRIL",
            MAY = "MAY",
            JUNE = "JUNE",
            JULY = "JULY",
            AUGUST = "AUGUST",
            SEPTEMBER = "SEPTEMBER",
            OCTOBER = "OCTOBER",
            NOVEMBER = "NOVEMBER",
            DECEMBER = "DECEMBER";
    /**
     * This Service class is used by the User endpoints and Manager endpoints
     */

    private final UserModelRepository userModelRepository;
    private final ExpenseModelRepository expenseModelRepository;

    @Autowired
    public ExpenseService(UserModelRepository userModelRepository, ExpenseModelRepository expenseModelRepository) {
        this.userModelRepository = userModelRepository;
        this.expenseModelRepository = expenseModelRepository;
    }

    public Optional<UserModel> getUserById(String userId) {
        return userModelRepository.findById(userId);
    }

    public Optional<ExpenseModel> getExpenseById(String expenseId) {
        return expenseModelRepository.findById(expenseId);
    }

    public ResponseModelListPayload<ExpenseModel> getAllExpenses() {
        List<ExpenseModel> allExpenses = expenseModelRepository.findAll();
        allExpenses.sort(new Comparator<ExpenseModel>() {
            @Override
            public int compare(ExpenseModel o1, ExpenseModel o2) {
                if(o1.getDatedOn().isAfter(o2.getDatedOn()))
                    return -1;
                else if(o1.getDatedOn().isBefore(o2.getDatedOn()))
                    return 1;
                return 0;
            }
        });
        return new ResponseModelListPayload<ExpenseModel>(ResponseModel.SUCCESS, allExpenses);
    }

    public ResponseModelListPayload<ExpenseModel> getAllExpenses(UserModel userModel) {

        List<ExpenseModel> allExpenses = expenseModelRepository.findAllExpensesByUser(userModel);
        allExpenses.sort(new Comparator<ExpenseModel>() {
            @Override
            public int compare(ExpenseModel o1, ExpenseModel o2) {
                if(o1.getDatedOn().isAfter(o2.getDatedOn()))
                    return -1;
                else if(o1.getDatedOn().isBefore(o2.getDatedOn()))
                    return 1;
                return 0;
            }
        });

        return new ResponseModelListPayload<ExpenseModel>(
                ResponseModel.SUCCESS,
                allExpenses
        );
    }

    public List<ExpenseModel> getAllExpenses(int monthNumber, UserModel userModel) {
        return expenseModelRepository.findAllExpensesByMonth(monthNumber, userModel);
    }

    public ResponseModelSinglePayload<ExpenseModel> addExpense(ExpenseModel expenseModel, UserModel userModel) {
        List<ExpenseModel> allExpensesByMonth = expenseModelRepository.findAllExpensesByMonth(expenseModel.getDatedOn().getMonth().getValue(), userModel);
        Integer totalExpense = expenseModel.getBillCost();
        for (ExpenseModel expenseModel1 : allExpensesByMonth) {
            totalExpense += expenseModel1.getBillCost();
        }
        if (totalExpense > 5000)
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Expense Not added as it is exceeding limit: Rs. 5000", null);

        expenseModel.setClaimedBy(userModel);
        expenseModelRepository.save(expenseModel);
        return new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, "Expense Added", expenseModel);
    }

    public ResponseModelSinglePayload<ExpenseModel> getExpense(String expenseId) {
        // Get the Expense Model by expenseId
        Optional<ExpenseModel> expenseById = getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense not found", null);

        return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.SUCCESS, expenseById.get());
    }

    @Transactional
    public ResponseModelSinglePayload<ExpenseModel> updateExpense(
            String expenseId,
            ExpenseModel expenseModelToUpdate,
            boolean user
    ) {
        //Find the expense model by Id
        Optional<ExpenseModel> expenseById = getExpenseById(expenseId);

        //If expense model is not found, return failure
        if (expenseById.isEmpty()) {
            return new ResponseModelSinglePayload<ExpenseModel>(ResponseModel.FAILURE, "Expense Not found", null);
        }

        ExpenseModel expenseModel = expenseById.get();

        List<ExpenseModel> allExpensesByMonth = expenseModelRepository.findAllExpensesByMonth(
                expenseModelToUpdate.getDatedOn().getMonth().getValue(),
                expenseModel.getClaimedBy()
        );
        Integer totalExpense = expenseModelToUpdate.getBillCost();
        for (ExpenseModel expenseModel1 : allExpensesByMonth) {
            if (!expenseModel.getExpenseId().equals(expenseModel1.getExpenseId()))
                totalExpense += expenseModel1.getBillCost();
        }
        if (totalExpense > 5000)
            return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "Expense Not added as it is exceeding limit: Rs. 5000", null);

        if (expenseModelToUpdate.getBillCost() != null && !Objects.equals(expenseModel.getBillCost(), expenseModelToUpdate.getBillCost())) {
            if (user) {
                if (expenseModel.getStatus().equals(ExpenseModel.REIMBURSED))
                    return new ResponseModelSinglePayload<>(ResponseModel.FAILURE, "This expense has already been Reimbursed. You cannot change the cost.", null);
            }
            expenseModel.setBillCost(expenseModelToUpdate.getBillCost());
        }
        if (expenseModelToUpdate.getBillNumber() != null && !Objects.equals(expenseModel.getBillNumber(), expenseModelToUpdate.getBillNumber())) {
            expenseModel.setBillNumber(expenseModelToUpdate.getBillNumber());
        }
        if (expenseModelToUpdate.getDatedOn() != null && !Objects.equals(expenseModel.getDatedOn(), expenseModelToUpdate.getDatedOn())) {
            expenseModel.setDatedOn(expenseModelToUpdate.getDatedOn());
        }
        if (expenseModelToUpdate.getStatus() != null && !Objects.equals(expenseModel.getStatus(), expenseModelToUpdate.getStatus())) {
            expenseModel.setStatus(expenseModelToUpdate.getStatus());
        }
        if (expenseModelToUpdate.getRemark() != null && !Objects.equals(expenseModel.getRemark(), expenseModelToUpdate.getRemark())) {
            expenseModel.setRemark(expenseModelToUpdate.getRemark());
        }

        // Return Successful with the updated expenses
        return new ResponseModelSinglePayload<ExpenseModel>(
                ResponseModel.SUCCESS, "Expense Updated Successfully",
                expenseModel
        );

    }

    public ResponseModel deleteExpenseById(String expenseId) {

        // Check if expense exists
        Optional<ExpenseModel> expenseById = getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Expense Not found");

        // If expense exists, delete it and return success message
        expenseModelRepository.deleteById(expenseId);
        return new ResponseModel(ResponseModel.SUCCESS, "Deleted Successfully");
    }

    public ResponseModelSinglePayload<UserDashboardModel> getUserDashboardModel(String month, UserModel userModel) {
        int monthNumber = getMonthNumber(month);
        List<ExpenseModel> allExpensesByMonth = getAllExpenses(monthNumber, userModel);
        Integer approvedExpense = 0, totalExpense = 0;

        for (ExpenseModel expenseModel : allExpensesByMonth) {
            totalExpense += expenseModel.getBillCost();
            if (expenseModel.getStatus().equals(ExpenseModel.REIMBURSED))
                approvedExpense += expenseModel.getBillCost();
        }
        UserDashboardModel userDashboardModel = new UserDashboardModel(
                totalExpense,
                totalExpense - approvedExpense,
                approvedExpense,
                allExpensesByMonth.size(),
                month
        );
        return new ResponseModelSinglePayload<>(ResponseModel.SUCCESS, "", userDashboardModel);

    }

    public int getMonthNumber(String month) {
        int monthNumber = 0;
        switch (month) {
            case JANUARY:
                monthNumber = 1;
                break;
            case FEBRUARY:
                monthNumber = 2;
                break;
            case MARCH:
                monthNumber = 3;
                break;
            case APRIL:
                monthNumber = 4;
                break;
            case MAY:
                monthNumber = 5;
                break;
            case JUNE:
                monthNumber = 6;
                break;
            case JULY:
                monthNumber = 7;
                break;
            case AUGUST:
                monthNumber = 8;
                break;
            case SEPTEMBER:
                monthNumber = 9;
                break;
            case OCTOBER:
                monthNumber = 10;
                break;
            case NOVEMBER:
                monthNumber = 11;
                break;
            case DECEMBER:
                monthNumber = 12;
                break;
            default:
                monthNumber = LocalDate.now().getMonth().getValue();
        }
        return monthNumber;
    }

    @Transactional
    public ResponseModel storeReceiptImage(MultipartFile receiptImage, String expenseId) throws IOException
    {
        // Check if expense exists
        Optional<ExpenseModel> expenseById = getExpenseById(expenseId);
        if (expenseById.isEmpty())
            return new ResponseModel(ResponseModel.FAILURE, "Expense Not found");

        String base64EncodedImage = Base64.getEncoder().encodeToString(receiptImage.getBytes());

        expenseById.get().setReceiptImage(base64EncodedImage);
        return new ResponseModel(ResponseModel.SUCCESS, "Receipt Image uploaded");

    }
}
