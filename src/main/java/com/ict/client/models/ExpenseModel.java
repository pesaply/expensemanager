package com.ict.client.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

//import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class ExpenseModel {

    /**
     * This is the Expense Model of the users. The Expenses of the Employees are stored here.
     */

    public static final String REIMBURSED = "REIMBURSED", NOT_REIMBURSED = "NOT_REIMBURSED";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String expenseId;
    private Long billNumber;
    private Integer billCost;

    @Lob
    private String receiptImage;
    private LocalDate datedOn;
    private String status = ExpenseModel.NOT_REIMBURSED;
    private String remark;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "userId")
    private UserModel claimedBy;

    public ExpenseModel() {
    }

    public ExpenseModel(Long billNumber, Integer billCost, String receiptImage, LocalDate datedOn, String status, String remark, UserModel claimedBy) {
        this.billNumber = billNumber;
        this.billCost = billCost;
        this.receiptImage = receiptImage;
        this.datedOn = datedOn;
        this.status = status;
        this.remark = remark;
        this.claimedBy = claimedBy;
    }

    public ExpenseModel(String expenseId, Long billNumber, Integer billCost, String receiptImage, LocalDate datedOn, String status, String remark, UserModel claimedBy) {
        this.expenseId = expenseId;
        this.billNumber = billNumber;
        this.billCost = billCost;
        this.receiptImage = receiptImage;
        this.datedOn = datedOn;
        this.status = status;
        this.remark = remark;
        this.claimedBy = claimedBy;
    }

    public Long getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(Long billNumber) {
        this.billNumber = billNumber;
    }

    public Integer getBillCost() {
        return billCost;
    }

    public void setBillCost(Integer billCost) {
        this.billCost = billCost;
    }

    public String getReceiptImage() {
        return receiptImage;
    }

    public void setReceiptImage(String receiptImage) {
        this.receiptImage = receiptImage;
    }

    public LocalDate getDatedOn() {
        return datedOn;
    }

    public void setDatedOn(LocalDate datedOn) {
        this.datedOn = datedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserModel getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(UserModel claimedBy) {
        this.claimedBy = claimedBy;
    }

    public String getExpenseId() {
        return expenseId;
    }

    @Override
    public String toString() {
        return "ExpenseModel{" +
                "expenseId='" + expenseId + '\'' +
                ", billNumber=" + billNumber +
                ", billCost=" + billCost +
                ", datedOn=" + datedOn +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", claimedBy=" + claimedBy +
                '}';
    }
}
