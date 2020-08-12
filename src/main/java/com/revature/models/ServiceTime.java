package com.revature.models;

import java.util.ArrayList;
import java.util.Scanner;

public class ServiceTime {
	
	private final AccountDAO acctDAO;
	private final UserDAO userDAO;
	
	public ServiceTime(AccountDAO acctDAO, UserDAO userDAO) {
		this.acctDAO = acctDAO;
		this.userDAO = userDAO;
	}
	
	//Method to login
	public User login(Scanner sin) {
		//System.out.println("Gets to Log In!");
		String potentUse = "";
		String potentPass = "";
		boolean propInput;
		User dudeGuy;
		do { //loop until a correct username/password combo is entered.
			propInput = true;
			System.out.print("Enter Username: ");
			potentUse = sin.nextLine();
			System.out.print("Enter Password: ");
			potentPass = sin.nextLine();
			//Checks User table for user matching credentials
			//if(NOTVALID){
			//  System.out.println("Your username or password was incorrect, please try again.");
			//  System.out.println("(Remember that your information is case-sensitive)");
			//  propInput = false;
			//}
			//else {
				//Grab User Info, for now placeholder guy
			    dudeGuy = new User(potentUse,"C");
			//}
		}while(propInput == false);
		return dudeGuy;
	}
	
	//Method to signup
	public User signup(Scanner sin) {
		//System.out.println("Gets to Sign Up!");
		String potentUse = "";
		String potentPass = "";
		boolean propInput;
		do { //loop until an unused username is entered.
			propInput = true;
			System.out.print("Please enter the username you would like (case-sensitive): ");
			potentUse = sin.nextLine();
			//Check User table for conflicting usernames
			//propInput = false if conflicts
			//System.out.println("Sorry, that username exists already. Please try again.");
		}while(propInput == false);
		System.out.print("Please enter your preferred password (case-sensitive): ");
		potentPass = sin.nextLine();
		//Double check maybe?
		
		//Pass potentUse and potentPass to User table and add a new row
		User newbie = new User(potentUse,"C");
		return newbie;
	}
	
	//Method to Logout
	
	//Changes the balance of a given account based on user u's active account
	//based on their permissions
	public boolean alterBalance(char action, int amount, User curUse) {
		switch(action) {
		case 'W':
			if (amount < 0) {
				System.out.println("Can't withdraw a negative amount");
				return false;
			}
			else if (amount > curUse.getActive().getBalance()) {
				System.out.println("Can't withdraw more money than exists in account");
				return false;
			}
			//Connect to SQL acct table, add amount to acctID
			break;
		case 'D':
			if (amount < 0) {
				System.out.println("Can't deposit a negative amount");
			}
			//Connect to SQL acct table, subtract amount from acctID
			break;
		default:
			return false;
		}
			
		return true;
	}
	
	private void printAccts(User curUse) {
		ArrayList<Account> bleh = curUse.getAccts();
		System.out.println();
		for(Account a: bleh) {
			System.out.print(a.getID() + " " + a.getType() + " " +
					a.getBalance()+ " " + a.getStatus());
			ArrayList<String> o = a.getOwners();
			for(int i = 0; i < o.size()-1; i++) {
				System.out.print(" " + o.get(i) + ",");
			}
			System.out.println(" " + o.get(o.size()-1));
		}
		System.out.println();
	}
	
	//Customer: 
	  //Account Transactions
	    //Withdraw
	    //Deposit
	    //Transfer
	  //Existing Accounts
	  //Switch Accounts
	  //New Account
	  //Give Account Access
	public void custMenu(User curUse,Scanner sin) {
		String check = "";
		boolean propInput = true;
		boolean loop = true;
		int amount = 0;
		Account to = null;
		
		while(loop) {
			//base menu
			System.out.println("CUSTOMER MENU:");
			System.out.println();
			System.out.println("[1] Account Transactions");
			System.out.println("[2] Existing Accounts");
			System.out.println("[3] Switch Accounts");
			System.out.println("[4] New Account");
			System.out.println("[5] Give Account Access");
			System.out.println("[6] Logout");
			System.out.println();
			System.out.println("Enter the action you'd like to perform:");
		
			//make choice
			do {
				propInput = true;
				check = sin.nextLine();
				switch(check) {
				case "1": //Account Transactions
					while(loop) {
						System.out.println();
						System.out.println("Would you like to ");
						System.out.println("[1] Withdraw");
						System.out.println("[2] Deposit");
						System.out.println("[3] Transfer");
						System.out.println("[4] Exit Account Transactions");
						System.out.println();
					
						do {
							propInput = true;
							check = sin.nextLine();
							sw:
							switch(check) {
							case "1": //Withdraw
								System.out.println("How much would you like to withdraw?");
								try {
									amount = Integer.parseInt(sin.nextLine());
								}
								catch(NumberFormatException e){
									System.out.println("Invalid amount, please use only numbers");
									break sw;
								}
								alterBalance('W',amount,curUse);
								break;
							case "2": //Deposit
								System.out.println("How much would you like to deposit?");
								try {
									amount = Integer.parseInt(sin.nextLine());
								}
								catch(NumberFormatException e){
									System.out.println("Invalid amount, please use only numbers");
									break sw;
								}
								alterBalance('D',amount,curUse);
								break;
							case "3": //Transfer
								System.out.println("How much would you like to transfer?");
								try {
									amount = Integer.parseInt(sin.nextLine());
								}
								catch(NumberFormatException e){
									System.out.println("Invalid amount, please use only numbers");
									break sw;
								}
								System.out.println("Account number you are transferring to");
								check = sin.nextLine();
								//CHECK ACCOUNT EXISTS
								//BREAK IF INVALID ACC NUM
								to = acctDAO.accountByID(check);
								//Pull money from current account
								alterBalance('W',amount,curUse);
								//Save current account
								Account from = curUse.getActive();
								//Change to target account
								curUse.setActive(to);
								//Deposit to target account
								alterBalance('D',amount,curUse);
								//Revert to original account
								curUse.setActive(from);
								break;
							case "4": //Exit Act Trnsctn
								loop = false;
								break;
							default: //Invalid Command
								System.out.println("Invalid Command. Enter a number 1-4: ");
								propInput = false;
							}
						}while(propInput == false);
						propInput = true;
						check = "1";
					}
					loop = true;
					break;
				case "2": //Existing Accounts
					System.out.println();
					System.out.println("Accounts you have access to");
					printAccts(curUse);
					break;
				case "3": //Switch Accounts
					System.out.println();
					System.out.println("What account would you like to switch to?");
					check = sin.nextLine();
					to = acctDAO.accountByID(check);
					if(curUse.getAccts().contains(to)) {
						curUse.setActive(to);
					}
					else {
						System.out.println("You don't have access to this account");
					}
					check = "3";
					
					break;
				case "4": //New Accounts
					//Ask if solo or joint account
					//Place new acct in Account table with closed status and balance 0
					break;
				case "5": //Give Account Access
					System.out.println();
					System.out.println("Would you like to give someone else access to account " 
							+ curUse.getActive() + "? \"Y\" for yes, anything else for no");
					check = sin.nextLine().toUpperCase();
					if(check.equals("Y")) {
						System.out.println("Enter username of user you'd like to give access to.");
						check = sin.nextLine();
						//Check if username in User table
						  //Check user does not already have access via Ownership table
						    //Check if account is solo in acct table
						    //if so change to joint
						    //Add account and username to ownership table
						  //Else say user already has access
						//Else say user does not exist
					}
					break;
				case "6": //Logout
					loop = false;
					break;
				default: //Invalid Command
					System.out.println("Invalid Command. Enter a number 1-6: ");
					propInput = false;
				}
			}while(propInput == false);
		}
	}

	//Employee: 
	  //Customers
	    //Select Customer Info
	      //Customer Username
	      //Customer Password(?)
	      //Customer Accounts
	        //Account Info (Give Account Number)
	  //Current Applications
	public void emplMenu(User curUse,Scanner sin) {
		String check = "";
		boolean propInput = true;
		boolean loop = true;
		
		while(loop) {
			System.out.println("EMPLOYEE MENU:");
			System.out.println();
			System.out.println("Would you like to:");
			System.out.println("[1] Customers");
			System.out.println("[2] Applications");
			System.out.println("[3] Logout");
			System.out.println();
			
			do {
				propInput = true;
				check = sin.nextLine();
				switch(check) {
				case "1"://Customers
					while(loop) {
						System.out.println("Would you like to see:");
						System.out.println();
					}
					break;
				case "2"://Applications
					break;
				case "3"://Logout
					loop = false;
					break;
				default:
					System.out.println("Invalid Command. Enter a number 1-3");
					propInput = false;
				}
				
			}while(propInput == false);
		}
	}
	
	//Admin:
	  //Employee Menu
	  //+
	      //Customer Accounts
	        //Account Info (Give Account Number)
	        //Change Account Balance (Give Account Number)
	          //Withdraw
	          //Deposit
	          //Transfer
	        //Cancel Account (Give Account Number)
	public void admnMenu(User curUse,Scanner sin) {
		
	}
}
