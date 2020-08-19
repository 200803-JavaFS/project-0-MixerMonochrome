package com.revature;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.*;
import com.revature.service.ServiceTime;

public class bankPortal {
	//Main function
	private static final Logger log = LogManager.getLogger(bankPortal.class); 
	public static void main(String[] args) {
		//VARIABLES GO HERE
		log.info("Start");
		ServiceTime takeAction = new ServiceTime(AccountDAO.getAcctDAO(),UserDAO.getUserDAO());
		String strDec;
		User curUse = null;
		boolean loop = true;
		
		//Open scanner for keyboard input
		Scanner sin = new Scanner(System.in);
		
		//
		while(loop) {
			//Startup Messages
			System.out.println("Hello! Welcome to ????????? Banking, where Banking happens.");
			
			
			do{
				System.out.println("Would you like to Log In [1], Sign Up [2], or Exit[3]?:");
				strDec = sin.nextLine();
				//Proceed to Log-in
				if(strDec.equals("1")) {
					curUse = takeAction.login(sin);
					log.info("Exits Login");
				}
				//Proceed to Sign-up
				else if(strDec.equals("2")){
					curUse = takeAction.signup(sin);
					log.info("Exits Signup");
				}
				else if(strDec.equals("3")){
					log.info("App Closing");
					System.out.println("Goodbye!");
					return;
				}
				//Incorrect Input
				else {
					log.info("Invalid input entered at Start Screen");
					System.out.println("Invalid Input (Must Enter 1, 2, or 3).");
					System.out.println("Would you like to Log In [1], Sign Up [2], or Exit [3]?:");
				}
				if(curUse != null) {
					log.info("Logged in as user " + curUse.getName());
					break;
				}
			}while(true);
		
			//Pull up Menu based on User Type (Different Methods?)
			System.out.println();
			String perm = curUse.getType();
			switch (perm) {
			case "C":
				log.info("Pulling up Customer Menu");
				takeAction.custMenu(curUse,sin);
				break;
			case "E":
				log.info("Pulling up Employee Menu");
				takeAction.emplMenu(curUse,sin);
				break;
			case "A":
				log.info("Pulling up Admin Menu");
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
				curUse = null;
				log.info("Restarting application logic.");
			}
			else {
				loop = false;
				log.info("App Closing");
			}
		}
		
		sin.close();
	}
}
