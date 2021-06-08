package app;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import models.Account;
import models.Transaction;
import models.User;
import services.AccountServiceImpl;
import services.TransactionServiceImpl;
import services.UserServiceImpl;
import utils.AppLogger;

public class Driver {

	private static Scanner scanner = new Scanner(System.in);
	private static UserServiceImpl us = new UserServiceImpl();
	private static AccountServiceImpl as = new AccountServiceImpl();
	private static TransactionServiceImpl ts = new TransactionServiceImpl();
	private static User loggedUser = null;
	
	public static void main(String[] args) {
		
		AppLogger.logger.info("Program Started.");				
		printMainMenu();
		
	}
	
	
	public static void printMainMenu() {
		System.out.println("Welcome to your friendly neighborhood banking application!");
		System.out.println("Select an option: ");
		System.out.println("\t1. Login \n\t2. Register \n\t3. Quit ");
		int input = scanner.nextInt();
		
		switch (input) {
			case 1: printLoginScreen(); break;
			case 2: printRegistrationScreen(); break;
			case 3: System.out.println("Have a good day!"); AppLogger.logger.info("User quit the program."); break;
			default : System.out.println("Please select a valid choice."); printMainMenu(); break;
		}
		
		
	}
	
	public static void printRegistrationScreen() {
		us.register(scanner);
		printMainMenu();
	}
	public static void printLoginScreen() {
		loggedUser = us.login(scanner);
		try {
			if (loggedUser.getStatus() == true) {
				printCustomerMenu();
			}
			else {
				printEmpMenu();
			}
		}
		catch(Exception e) {
			printMainMenu();
		}	
		
	}
	public static void printCustomerMenu() {
		System.out.println("Customer Menu");
		System.out.println("\t1. View Accounts \n\t2. New Account Application \n\t3. Logout");
		
		int input = scanner.nextInt();
		
		switch(input) {
			case 1: printAccountList(loggedUser); break;
			case 2: printApplicationScreen(loggedUser); break;
			case 3: AppLogger.logger.info(loggedUser.getUsername()+" logged out"); printMainMenu();  break;
			default: System.out.println("Select a valid option."); printCustomerMenu(); break;
		}
		
	}
	public static void printApplicationScreen(User customer) {
		System.out.println("Application for a new account.");
		System.out.println("Enter a name for the account: ");
		scanner.nextLine();
		String inputName = scanner.nextLine();
		
		
		System.out.println("Enter the starting balance for the account: ");
		double inputBal = scanner.nextDouble();
		Account tempAcct = new Account(inputName.toString(),inputBal,"pending",customer);
		
		if(as.add(tempAcct)) {
			System.out.println("Thank you! Your account has been submitted for approval.");
			
		}
		else{
			System.out.println("Oops! Something went wrong, please try again later.");
			
		};
		
		printCustomerMenu();
		
		
	}
	public static void printAccountList(User customer) {
		System.out.println(customer.getFirstName() + " " + customer.getLastName() +"'s Accounts:");
		ArrayList<Account> list = as.getByUser(customer);
		
		for (int i = 0; i<list.size(); i++) {
			if(list.get(i).getAcctStatus().equals("active")) {
				System.out.println( "\t" + (i+1) +". " + list.get(i).getAcctName());
			}
			else {
				System.out.println( "\t" + (i+1) +". " + list.get(i).getAcctName() + " (Pending)");
			}
		}	
		
		
		System.out.println("\t"+ (list.size()+1) + ". Back");
		int input = scanner.nextInt();
		if ((input == (list.size()+1)) && (loggedUser.getStatus() == false)) {
			printEmpMenu();
		}
		else if(input == (list.size()+1)) {
			printCustomerMenu();
		}
		else {
			printAccountMenu(list.get(input-1));
		}
		
	}
	public static void printAccountMenu(Account acct) {
		System.out.println(acct.getAcctName() + " -------- Balance: "+ acct.balanceToString());
		System.out.println("Account Status: "+ acct.getAcctStatus());
		System.out.println("Select an option: ");
		if (acct.getAcctStatus().equals("pending")) {
			System.out.println("\t4. Back");
		}
		else {
			System.out.println("\t1. Withdraw \n\t2. Deposit \n\t3. Transfer \n\t4. Back");
		}
		
		int input = scanner.nextInt();
		
		switch(input) {
			case 1: printWithdrawScreen(acct); break;
			case 2: printDepositScreen(acct); break;
			case 3: printTransferMenu(acct); break;
			case 4: printAccountList(acct.getAcctOwner()); break;
			default: System.out.println("Select a valid option."); printAccountMenu(acct); break;
		}
	}
	public static void printWithdrawScreen(Account acct) {
		if(acct.getAcctStatus().equals("pending")) {
			printAccountMenu(acct);
			return;
		}
		NumberFormat dollars = NumberFormat.getCurrencyInstance();
		System.out.println("Available Balance: " + acct.balanceToString());
		System.out.println("How much would you like to withdraw? ");
		double input = scanner.nextDouble();
		System.out.println("You want to withdraw: " + dollars.format(input)+ " is this correct?");
		System.out.println("\t1. Yes \n\t2. No \n\t3. Cancel");
		int answer = scanner.nextInt();
		
		switch(answer) {
			case 1 : {
				if (input > acct.getAcctBalance()) {
					System.out.println("That exceeds your available balance!");
					AppLogger.logger.info(loggedUser.getUsername()+" tried to withdraw "+ dollars.format(input) + " from " + acct.getAcctName() + " "
							+ "the available balance at the time was " + dollars.format(acct.getAcctBalance()));
					printWithdrawScreen(acct);
					break;
				}
				else if(input < 0) {
					System.out.println("Please input a positive number.");
					AppLogger.logger.info(loggedUser.getUsername()+" tried to withdraw "+ dollars.format(input) + " from " + acct.getAcctName() + " "
							+ "the available balance at the time was " + dollars.format(acct.getAcctBalance()));
					printWithdrawScreen(acct);
					break;
				}
				acct.setAcctBalance(acct.getAcctBalance()-input);
				as.update(acct);
				Transaction temp = new Transaction("withdraw",input,acct,acct.getAcctOwner());
				ts.add(temp);
				System.out.println("New Balance: "+ acct.balanceToString());	
				AppLogger.logger.info(loggedUser.getUsername()+" withdrew "+ dollars.format(input) + " from " + acct.getAcctName() + " "
						+ "the new balance at the time was " + acct.balanceToString());
				printAccountMenu(acct);
				break;
			}
			case 2 : printWithdrawScreen(acct); break;
			case 3 : printAccountMenu(acct); break;
			default: System.out.println("Please select a valid choice."); break;
			
		}
		
	}
	public static void printDepositScreen(Account acct) {
		
		if(acct.getAcctStatus().equals("pending")) {
			printAccountMenu(acct);
			return;
		}
		NumberFormat dollars = NumberFormat.getCurrencyInstance();
		System.out.println("Available Balance: " + acct.balanceToString());
		System.out.println("How much would you like to deposit? ");
		double input = scanner.nextDouble();
		System.out.println("You want to deposit: " + dollars.format(input)+ " is this correct?");
		System.out.println("\t1. Yes \n\t2. No \n\t3. Cancel");
		int answer = scanner.nextInt();
		
		switch(answer) {
			case 1 : {
				if(input < 0) {
					System.out.println("Please input a positive number.");
					AppLogger.logger.info(loggedUser.getUsername()+" tried to depoit "+ dollars.format(input) + " to " + acct.getAcctName() + " "
							+ "the balance at the time was " + dollars.format(acct.getAcctBalance()));
					printDepositScreen(acct);
					break;
				}
				acct.setAcctBalance(acct.getAcctBalance()+input);
				as.update(acct);
				Transaction temp = new Transaction("deposit",input,acct,acct.getAcctOwner());
				ts.add(temp);
				System.out.println("New Balance: "+ acct.balanceToString());
				AppLogger.logger.info(loggedUser.getUsername()+" depoited "+ dollars.format(input) + " to " + acct.getAcctName() + " "
						+ "the new balance at the time was " + acct.balanceToString());
				printAccountMenu(acct);
				break;
			}
			case 2 : printDepositScreen(acct); break;
			case 3 : printAccountMenu(acct); break;
			default: System.out.println("Please select a valid choice."); break;
			
		}
	}
	public static void printTransferMenu(Account fromAcct) {
		System.out.println("Transfer Menu");
		System.out.println("\t1. Transfer Money \n\t2. View Account's Pending Transfers \n\t3. Back");
		int input = scanner.nextInt();
		
		switch(input) {
			case 1: printTransferMoneyScreen(fromAcct); break;
			case 2: printPendingTransfersScreen(fromAcct); break;
			case 3: printAccountMenu(fromAcct); break;
			default: System.out.println("Please select a valid choice."); printTransferMenu(fromAcct); break;
		}
	}
	
	public static void printTransferMoneyScreen(Account fromAcct) {
		System.out.println("Which account do you want to transfer money to?");
		ArrayList<Account>list = as.getByUser(loggedUser);
		list.remove(fromAcct);
		for (int i = 0; i<list.size(); i++) {
			if(list.get(i).getAcctStatus().equals("active")) {
				System.out.println( "\t" + (i+1) +". " + list.get(i).getAcctName());
			}
			else {
				list.remove(list.get(i));
			}
		}
		System.out.println("\t" +(list.size()+1) + ". Back");
		int input = scanner.nextInt();
		Account toAcct = list.get(input-1);
		System.out.println("How much would you like to send to " + toAcct.getAcctName()+ "?");
		double amount = scanner.nextDouble();
		
		if (amount > fromAcct.getAcctBalance()) {
			System.out.println("That exceeds your available balance!");
			printTransferMoneyScreen(fromAcct);
			return;
		}
		else if(amount < 0) {
			System.out.println("Please input a positive number.");
			printTransferMoneyScreen(fromAcct);
			return;
		}
		Transaction newTran = new Transaction("transfer",amount, fromAcct, fromAcct.getAcctOwner(),"pending",toAcct);
		ts.add(newTran);
		System.out.println("The transfer was posted to " + toAcct.getAcctName()+". The money won't be removed until it is accepted.");
		printTransferMenu(fromAcct);
		
	}
	public static void printPendingTransfersScreen(Account acct) {
		System.out.println("Pending Transfers");
		ArrayList<Transaction> list = ts.getPending(acct);
		
		for (Transaction t : list) {
			System.out.println("\t"+ (list.indexOf(t)+1) + ". " + t.getAmount()+ " From: " + t.getAccount().getAcctName());
		}
		System.out.println("\t" +(list.size()+1) + ". Back");
		int input = scanner.nextInt();
		
		if (input == (list.size()+1)) {
			printTransferMenu(acct);
			return;
		}
		Transaction selectedTran = list.get(input-1);
		
		System.out.println(selectedTran.getAmount()+ "From: " + selectedTran.getAccount()+ "Sent: " + selectedTran.getTimestamp());
		System.out.println("\t1. Accept \n\t2. Reject \n\t3. Back");
		input = scanner.nextInt();
		
		switch(input) {
			case 1: ts.accept(selectedTran); printPendingTransfersScreen(acct); break;
			case 2: ts.reject(selectedTran); printPendingTransfersScreen(acct); break;
			case 3: printPendingTransfersScreen(acct); break;
			default : System.out.println("Please input a valid selection."); printPendingTransfersScreen(acct); break;
		}
		
		
		
	}
	public static void printEmpMenu() {
		System.out.println("Employee Menu");
		System.out.println("\t1. Account Applications \n\t2. Customers \n\t3. Transactions \n\t"
				+ "4. Logout");
		
		int input = scanner.nextInt();
		
		switch(input) {
			case 1: printApplicationList(); break;
			case 2: printCustList(); break;
			case 3: printTransactionsList(); break;
			case 4: printMainMenu(); break;
			default: System.out.println("Select a valid option."); printEmpMenu(); break;
		}
	}
	public static void printApplicationList() {
		
		System.out.println("Pending Accounts");
		ArrayList<Account> list = as.getPending();
		
		for(Account a : list) {
			System.out.println("\t" + (list.indexOf(a)+1) + ". " + a.getAcctName());
		}
		System.out.println("\t" + (list.size()+1) + ". " + "Back");
		int input = scanner.nextInt();
		if (input == (list.size()+1)) {
			printEmpMenu();
			return;
		}
		
		printAccountDecisionScreen(list.get(input-1));
		
	}
	public static void printAccountDecisionScreen(Account application) {
		System.out.println(application.getAcctName() + " -------- Balance: "+ application.balanceToString());
		System.out.println("Account Owner: "+ application.getAcctOwner().getLastName()+", " + application.getAcctOwner().getFirstName());
		System.out.println("Select an option: ");
		System.out.println("\t1. Approve \n\t2. Deny \n\t3. Back");
		
		int input = scanner.nextInt();
		
		switch(input){
			case 1: {
				application.setAcctStatus("active");
				as.update(application);
				System.out.println("Approved!"); 
				printApplicationList();
				break;
			}
			case 2:{
				application.setAcctStatus("denied");
				as.delete(application);
				System.out.println("Rejected!");
				printApplicationList();
				break;
			}
			case 3: printApplicationList(); break;
			default: System.out.println("Select a valid option. "); printAccountDecisionScreen(application); break;
		}
	}
	public static void printCustList() {
		System.out.println("Customers List");
		
		ArrayList<User> list = us.getAll();
		
		for(User c : list) {
			System.out.println("\t" + (list.indexOf(c)+1) + ". " + c.getLastName() + ", " + c.getFirstName());
		}
		System.out.println("\t" + (list.size()+1) + ". " + "Back");
		int input = scanner.nextInt();
		if (input == (list.size()+1)) {
			printEmpMenu();
			return;
		}
		
		printAccountList(list.get(input-1));
	}
	public static void printTransactionsList() {
		System.out.println("Transactions List");
		ArrayList<Transaction> list = ts.getAll();
		
		for (Transaction t : list) {
			System.out.println("\t " + t.toString());
		}
		System.out.println("\t1. Press any key to go back.");
		String input = scanner.next();
		switch(input) {
			default: printEmpMenu();break;
		}
	}
	
	

	
}
