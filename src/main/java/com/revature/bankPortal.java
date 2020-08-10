package com.revature;

import java.util.*;

import com.revature.models.*;

public class bankPortal {
	

	//Method to login
	public static User login(Scanner sin) {
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
	public static User signup(Scanner sin) {
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
	public static boolean alterBalance(char action, int amount, User curUse) {
		switch(action) {
		case 'W':
			if (amount < 0) {
				System.out.println("Can't withdraw a negative amount");
				return false;
			}
			else if (amount > curUse.getBalance()) {
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
	
	//Customer: 
	  //Account Transactions
	    //Withdraw
	    //Deposit
	    //Transfer
	  //Existing Accounts
	  //Switch Accounts
	  //New Account
	  //Give Account Access
	public static void custMenu(User curUse,Scanner sin) {
		String check = "";
		boolean propInput = true;
		boolean loop = true;
		
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
				case "1":
					break;
				case "2":
					
					break;
				case "3":
					break;
				case "4":
					break;
				case "5":
					break;
				case "6":
					loop = false;
					propInput = false;
					break;
				default:
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
	public static void emplMenu(User curUse,Scanner sin) {
		
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
	public static void admnMenu(User curUse,Scanner sin) {
		
	}
	
	//Main function
	public static void main(String[] args) {
		//VARIABLES GO HERE
		String strDec = "";
		boolean properInput = true;
		User curUse = new User();
		boolean loop = true;
		
		//Open scanner for keyboard input
		Scanner sin = new Scanner(System.in);
		
		//
		while(loop) {
			//Startup Messages
			System.out.println("Hello! Welcome to [NAME] Banking");
			System.out.println("Would you like to Log In [1] or Sign Up [2]?:");
			
			//Login/Sign up section
			do {
				properInput = true;
				strDec = sin.nextLine();
				//Proceed to Log-in
				if(strDec.equals("1")) {
					curUse = login(sin);
				}
				//Proceed to Sign-up
				else if(strDec.equals("2")){
					curUse = signup(sin);
				}
				//Incorrect Input
				else {
					System.out.println("Invalid Input (Must Enter 1 or 2).");
					System.out.println("Would you like to Log In [1] or Sign Up [2]?:");
					properInput = false;
				}
			}while(properInput == false);
		
			//Pull up Menu based on User Type (Different Methods?)
			System.out.println();
			String perm = curUse.getType();
			switch (perm) {
			case "C":
				custMenu(curUse,sin);
				break;
			case "E":
				emplMenu(curUse,sin);
				break;
			case "A":
				admnMenu(curUse,sin);
				break;
			default:
				System.out.println("??? Something went Very Wrong");
				System.exit(1);
			}
			
			System.out.println();
		    System.out.print("Signed out.");
		    System.out.println();
		    System.out.println("Would you like to sign in as a different user [1] or Exit the application [Anything else]?");
			strDec = sin.nextLine();
			
			if(strDec.equals("1")) {
				curUse = new User();
			}
			else {
				loop = false;
			}
		}
		
		sin.close();
	}
}
