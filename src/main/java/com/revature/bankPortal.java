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
			    dudeGuy = new User();
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
	
	//Main function
	public static void main(String[] args) {
		//VARIABLES GO HERE
		int intDec = 0;
		String strDec = "";
		boolean properInput = true;
		
		//Open scanner for keyboard input
		Scanner sin = new Scanner(System.in);
		
		//Startup Messages
		System.out.println("Hello! Welcome to [NAME] Banking");
		System.out.println("Would you like to Log In [1] or Sign Up [2]?:");
		
		do {
			properInput = true;
			intDec = sin.nextInt();
			//Proceed to Log-in
			if(intDec == 1) {
				sin.nextLine();
				login(sin);
			}
			//Proceed to Sign-up
			else if(intDec == 2){
				sin.nextLine();
				signup(sin);
			}
			//Incorrect Input
			else {
				System.out.println("Invalid Input (Must Enter 1 or 2).");
				System.out.println("Would you like to Log In [1] or Sign Up [2]?:");
				properInput = false;
			}
		}while(properInput == false);
		
		
		
		sin.close();
	}
}
