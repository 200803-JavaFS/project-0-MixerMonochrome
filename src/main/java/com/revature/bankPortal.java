package com.revature;

import java.util.*;
import com.revature.models.*;

public class bankPortal {
	//Main function
	public static void main(String[] args) {
		//VARIABLES GO HERE
		
		ServiceTime takeAction = new ServiceTime(AccountDAO.getAcctDAO(),UserDAO.getUserDAO());
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
					curUse = takeAction.login(sin);
				}
				//Proceed to Sign-up
				else if(strDec.equals("2")){
					curUse = takeAction.signup(sin);
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
				takeAction.custMenu(curUse,sin);
				break;
			case "E":
				takeAction.emplMenu(curUse,sin);
				break;
			case "A":
				takeAction.admnMenu(curUse,sin);
				break;
			default:
				System.out.println("??? Something went Very Wrong");
				System.exit(1);
			}
			 
			//Logged Out
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
