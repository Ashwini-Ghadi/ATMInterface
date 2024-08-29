package com.main;

import java.util.*;
import com.account.UserBankAccount;
import com.atm.ATMFeatures;

public class ATMInterfaceMain {

	public static void main(String[] args) {
		 Map<String, UserBankAccount> accounts = new HashMap<>();
	        accounts.put("1234", new UserBankAccount(1000, "1234", "Abhishek"));
	        accounts.put("5678", new UserBankAccount(5000, "5678", "Kirti"));

	        new ATMFeatures(accounts);
	}

}
