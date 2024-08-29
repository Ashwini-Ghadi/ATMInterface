package com.account;

public class UserBankAccount {
	 private double balance;
	    private String pin;
	    private String name;

	    public UserBankAccount(double balance, String pin, String name) {
	        this.balance = balance;
	        this.pin = pin;
	        this.name = name;
	    }

	    public double getBalance() {
	        return balance;
	    }

	    public void setBalance(double balance) {
	        this.balance = balance;
	    }

	    public String getPin() {
	        return pin;
	    }

	    public void setPin(String pin) {
	        this.pin = pin;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public void deposit(double amount) {
	        if (amount > 0) {
	            balance += amount;
	            System.out.println("Deposited: " + amount);
	        } else {
	            System.out.println("Invalid deposit amount.");
	        }
	    }

	    public boolean withdraw(double amount) {
	        if (amount > 0 && amount <= balance) {
	            balance -= amount;
	            System.out.println("Withdrew: " + amount);
	            return true;
	        } else if (amount > balance) {
	            System.out.println("Insufficient balance.");
	            return false;
	        } else {
	            System.out.println("Invalid withdrawal amount.");
	            return false;
	        }
	    }

	    public void checkBalance() {
	        System.out.println("Current balance: " + balance);
	    }

	    public boolean verifyPin(String inputPin) {
	        return this.pin.equals(inputPin);
	    }
}
