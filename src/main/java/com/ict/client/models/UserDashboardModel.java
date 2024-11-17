package com.ict.client.models;

public class UserDashboardModel {

    private Integer totalExpense;
    private Integer pendingExpense;
    private Integer approvedExpense;
    private Integer numberExpenses;
    private String month;

    public UserDashboardModel() {
    }

    public UserDashboardModel(Integer totalExpense, Integer pendingExpense, Integer approvedExpense, Integer numberExpenses, String month) {
        this.totalExpense = totalExpense;
        this.pendingExpense = pendingExpense;
        this.approvedExpense = approvedExpense;
        this.numberExpenses = numberExpenses;
        this.month = month;
    }

    public Integer getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Integer totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Integer getPendingExpense() {
        return pendingExpense;
    }

    public void setPendingExpense(Integer pendingExpense) {
        this.pendingExpense = pendingExpense;
    }

    public Integer getApprovedExpense() {
        return approvedExpense;
    }

    public void setApprovedExpense(Integer approvedExpense) {
        this.approvedExpense = approvedExpense;
    }

    public Integer getNumberExpenses() {
        return numberExpenses;
    }

    public void setNumberExpenses(Integer numberExpenses) {
        this.numberExpenses = numberExpenses;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
