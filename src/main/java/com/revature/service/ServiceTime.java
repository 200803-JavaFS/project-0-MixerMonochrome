package com.revature.service;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;

public class ServiceTime {
	
	private static final Logger log = LogManager.getLogger(ServiceTime.class); 
	private final AccountDAO acctDAO;
	private final UserDAO userDAO;

	public ServiceTime(AccountDAO acctDAO, UserDAO userDAO) {
		this.acctDAO = acctDAO;
		this.userDAO = userDAO;
	}

	// Method to login
	public User login(Scanner sin) {
		log.info("Login method entered");
		String potentUse;
		String potentPass;
		boolean propInput;
		User dudeGuy = new User();
		do { // loop until a correct username/password combo is entered.
			propInput = true;
			System.out.print("Enter Username: ");
			potentUse = sin.nextLine();
			System.out.print("Enter Password: ");
			potentPass = sin.nextLine();
			log.info("Checking credentials...");
			if (userDAO.userExists(potentUse) && userDAO.checkPass(potentUse, potentPass)) {
				log.info("Match found!");
				dudeGuy = userDAO.findUserByName(potentUse);
			} else {
				log.info("No Match.");
				System.out.println("Your username or password was incorrect, please try again.");
				System.out.println("(Remember that your information is case-sensitive)");
				System.out.println("Would you like to return to the previous screen? [Y] for yes, else no.");
				if(sin.nextLine().toUpperCase().equals("Y")) {
					log.info("Returning to Opening Screen.");
					return null;
				}
				else {
					propInput = false;
				}
			}
		} while (propInput == false);
		return dudeGuy;
	}

	// Method to signup
	public User signup(Scanner sin) {
		log.info("Signup method entered");
		String check;
		String potentUse;
		String potentPass;
		String fName;
		String lName;
		String phoneNum = null;
		String address = null;

		boolean propInput;
		do { // loop until an unused username is entered.
			propInput = true;
			System.out.print("Please enter the username you would like (case-sensitive): ");
			potentUse = sin.nextLine();
			log.info("Comparing username...");
			if (userDAO.userExists(potentUse)) {
				log.info("Match found.");
				propInput = false;
				System.out.println(
						"Sorry, the username " + potentUse + " is already in use. Please select a different name.");

				System.out.println("Would you like return to the previous screen? [Y] for yes, else no.");
				if(sin.nextLine().toUpperCase().equals("Y")) {
					log.info("Returning to opening screen.");
					return null;
				}
			}
		} while (propInput == false);
		System.out.print("Please enter your preferred password (case-sensitive): ");
		potentPass = sin.nextLine();
		// Double check maybe?

		// Pass potentUse and potentPass to User table and add a new row
		System.out.print("First name: ");
		fName = sin.nextLine();
		System.out.print("Last name: ");
		lName = sin.nextLine();
		System.out.println("Would you like to enter a phone number? [Y] for yes, else no.");
		check = sin.nextLine().toUpperCase();
		if(check.equals("Y")) {
			System.out.print("Phone Number (No hyphens, no parenthesis): ");
			phoneNum = sin.nextLine();
		}
		System.out.println("Would you like to enter an address? [Y] for yes, else no.");
		check = sin.nextLine().toUpperCase();
		if(check.equals("Y")) {
			System.out.print("Address: ");
			address = sin.nextLine();
		}

		User newbie = new User(potentUse, "C", fName, lName, phoneNum, address);
		log.info("Creating User and pushing to database.");
		userDAO.createUser(newbie, potentPass);
		return newbie;
	}

	public void callAlter(char action, User curUse, Scanner sin) {
		log.info("Enters callAlter method.");
		int amount;
		int aId;
		Account to;
		String actionFull;
		if(action == 'W') {
			log.info("Setup for withdrawal.");
			actionFull = "withdraw";
		}
		else if(action == 'D') {
			log.info("Setup for deposit");
			actionFull = "deposit";
		}
		else if (action == 'T') {
			log.info("Setup for transfer");
			actionFull = "transfer";
		}
		else {
			log.warn("How did. You do this. This isn't a valid transaction type.");
			actionFull = "VERY WRONG";
		}
		System.out.println("How much would you like to " + actionFull + "?");
		try {
			amount = Integer.parseInt(sin.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid amount, please use only numbers");
			return;
		}
		if(action != 'T') {
			alterBalance(action, amount, curUse);
		}
		else {
			System.out.println("Account number you are transferring to");
			try {
				aId = Integer.parseInt(sin.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid account number, please use only numbers");
				return;
			}
			log.info("Checking if account " + aId + " exists.");
			if (acctDAO.accountExists(aId)) {
				log.info("Account exists.");
				log.info("Grabbing account " + aId + "from Database.");
				to = acctDAO.getAccountByID(aId);
				log.info("Account retrieved.");
				// Pull money from current account
				log.info("Withdrawing from account " + curUse.getActive().getID());
				if(alterBalance('W', amount, curUse)) {
					// Save current account
					Account from = curUse.getActive();
					// Change to target account
					curUse.setActive(to);
					// Deposit to target account
					log.info("Depositing to account "+ aId);
					alterBalance('D', amount, curUse);
					// Revert to original account
					curUse.setActive(from);
				}
			} else {
				log.info("Accound doesn't exist.");
				System.out.println("Account number " + aId + " does not exist");
			}
		}
	}

	// Changes the balance of a given account based on user u's active account
	// based on their permissions
	public boolean alterBalance(char action, int amount, User curUse) {
		log.info("Enters alterBalance method.");
		Account a = curUse.getActive();
		switch (action) {
		case 'W':
			log.info("Withdrawal checks.");
			if (amount < 0) {
				System.out.println("Can't withdraw a negative amount");
				log.info("Withdrawal failed.");
				return false;
			} else if (amount > curUse.getActive().getBalance()) {
				System.out.println("Can't withdraw more money than exists in account");
				log.info("Withdrawal failed.");
				return false;
			}
			a.setBalance(a.getBalance() - amount);
			acctDAO.updateAccount(a);
			break;
		case 'D':
			log.info("Deposit checks");
			if (amount < 0) {
				System.out.println("Can't deposit a negative amount");
				log.info("Deposit failed.");
			}
			a.setBalance(a.getBalance() + amount);
			log.info("New balance updated in Database");
			acctDAO.updateAccount(a);
			break;
		default:
			log.warn("Not a valid transaction type. Seriously, I don't know how you got here, they're HARDCODED.");
			return false;
		}

		return true;
	}
	
	private void switchAccount(Scanner sin, User curUse) {
		log.info("Entered switchAccount method");
		int aId;
		System.out.println();
		System.out.println("What account would you like to switch to?");
		try {
			aId = Integer.parseInt(sin.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid account, please use only numbers");
			return;
		}
		if(curUse.getType().equals("C")) {
			log.info("User is a customer, checking if they have access to account they entered.");
			if (curUse.getAccts().contains(aId)) {
				log.info("Grabbing account.");
				curUse.setActive(acctDAO.getAccountByID(aId));
			} else {
				System.out.println("You don't have access to this account");
			}
		}
		else if(curUse.getType().equals("A")){
			log.info("User is an admin, checking if account exists.");
			if(acctDAO.accountExists(aId)) {
				log.info("Grabbing account");
				curUse.setActive(acctDAO.getAccountByID(aId));
				System.out.println("Active account set to Account: " + curUse.getActive().getID());
			}
			else {
				System.out.println("Account " + aId + " does not exist.");
			}	
		}
		else{
			log.info("User is not an admin Or a customer and should not have access to this method.");
			System.out.println("You shouldn't even be able to access this I don't know what happened.");
		}
	}
	
	private void approveDeny(Scanner sin) {
		log.info("Enters approveDeny method");
		String check;
		int aId;
		boolean loop = true;
		log.info("Grabs pending accounts from Database.");
		ArrayList<Account> accounts = acctDAO.getAccountsByStatus("O");

		while(loop) {
			if(accounts.isEmpty()) {
				System.out.println("There are no pending accounts");
				return;
			}
			printAccts(accounts);
			System.out.println("Select Account (enter [E] to go back)");
			check = sin.nextLine();
			if (check.toUpperCase().equals("E")) {
				log.info("Going back to menu.");
				loop = false;
			} else {
				log.info("Checking entered account is actually a number.");
				try {
					aId = Integer.parseInt(check);
				} catch (NumberFormatException e) {
					System.out.println("Invalid Account, please use only numbers");
					return;
				}
				log.info("Checking if entered account exists.");
				if (acctDAO.accountExists(aId)) {
					log.info("Retrieving account");
					Account acct = acctDAO.getAccountByID(aId);
					log.info("Double check account " + aId + " is pending");
					if (acct.getStatus().equals("O")) {
						System.out.println(
								"Would you like to [A] Approve this account or [D] Deny this account?");
						check = sin.nextLine();
						if (check.toUpperCase().equals("A")) {
							acct.setStatus("A");
							log.info("Updating account status to Approved in Database");
							acctDAO.updateAccount(acct);
							log.info("Updating pending accounts list");
							accounts = acctDAO.getAccountsByStatus("O");
						} else if (check.toUpperCase().equals("D")) {
							acct.setStatus("D");
							log.info("Updating account status to Denied in Database");
							acctDAO.updateAccount(acct);
							log.info("Updating pending accounts list");
							accounts = acctDAO.getAccountsByStatus("O");
						} else {
							System.out.println("Invalid action. Returning to Account Selection.");
						}
					} else {
						System.out.println("That account is not open for status change at this time.");
					}
				} else {
					System.out.println("Account does not exist");
				}
			}
		}
	}

	private void printAccts(ArrayList<Account> bleh) {
		log.info("Enters printAccts method");
		System.out.println();
		for (Account a : bleh) {
			System.out.println(a.toString());
		}
		System.out.println();
	}

	private void printUsers(ArrayList<User> customers) {
		log.info("Enters printUsers method");
		System.out.println();
		for (User u : customers) {
			System.out.println(u.toString());
		}
		System.out.println();
	}

	// Customer:
	// Account Transactions
	  // Withdraw
	  // Deposit
	  // Transfer
	// Existing Accounts
	// Switch Accounts
	// New Account
	// Give Account Access
	public void custMenu(User curUse, Scanner sin) {
		log.info("Enters custMenu method");
		String check;
		boolean propInput = true;
		boolean loop = true;
		Account to;

		while (loop) {
			log.info("Starts top loop.");
			// base menu
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

			// make choice
			do {
				propInput = true;
				check = sin.nextLine();
				switch (check) {
				case "1": // Account Transactions
					log.info("Chose Account Transactoins");
					log.info("Checks if User has chosen an account to perform transactions on, and if that account is active.");
					if (curUse.getActive() == null) {
						System.out.println("You have not chosen an account to alter yet, please Switch Accounts first.");
						break;
					} else if (!curUse.getActive().getStatus().equals("A")) {
						System.out.println("Your chosen account is not approved for transactions.");
						break;
					}
					while (loop) {
						log.info("Top of Transaction Loop");
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
							switch (check) {
							case "1": // Withdraw
								log.info("Chose Withdraw");
								callAlter('W',curUse,sin);
								break;
							case "2": // Deposit
								log.info("Chose Deposit");
								callAlter('D',curUse,sin);
								break;
							case "3": // Transfer
								log.info("Chose Transfer");
								callAlter('T',curUse,sin);
								break;
							case "4": // Exit Act Trnsctn
								log.info("Chose Exit Account Transaction");
								loop = false;
								break;
							default: // Invalid Command
								System.out.println("Invalid Command. Enter a number 1-4: ");
								propInput = false;
							}
						} while (propInput == false);
						propInput = true;
						check = "1";
					}
					loop = true;
					break;
				case "2": // Existing Accounts
					log.info("Chose Existing Accounts");
					System.out.println();
					if(curUse.getAccts().isEmpty()) {
						System.out.println("You don't have any accounts set up yet.");
						break;
					}
					ArrayList<Account> accounts = acctDAO.getAccountsByUser(curUse.getName());
					if(!accounts.isEmpty()) {
						System.out.println("Accounts you have access to");
						printAccts(accounts);
					}
					else {
						System.out.println("You don't have any accounts yet");
					}
					System.out.println("Press Enter to proceed");
					sin.nextLine();
					break;
				case "3": // Switch Accounts
					log.info("Chose Switch Accounts");
					switchAccount(sin, curUse);
					break;
				case "4": // New Accounts
					log.info("Chose New Account");
					// Ask if solo or joint account
					System.out.println(
							"Would you like to create a solo account [S] or a joint account [J]? (Type anything else to exit)");
					check = sin.nextLine().toUpperCase();
					if (!check.equals("S") && !check.equals("J")) {
						break;
					}

					// Place new acct in Account table with pending status and balance 0
					to = new Account(check, 0, "O");
					acctDAO.addAccount(to);
					System.out.println("New account made with id " + to.getID());
					ArrayList<Integer> temp = curUse.getAccts();
					temp.add(to.getID());
					curUse.setAccts(temp);
					acctDAO.addUserAccess(to, curUse.getName());
					curUse.setNumAccts(curUse.getNumAccts()+1);
					userDAO.updateUser(curUse);
					if (check.equals("J")) {
						System.out.println("Enter the username you'd like to share this account with.");
						while (loop) {
							check = sin.nextLine();
							if (userDAO.userExists(check)) {
								acctDAO.addUserAccess(to, check);
							} else {
								System.out.println(
										"User does not exist. Check spelling and remember usernames are case-sensitive.");
							}
							System.out.println("Would you like to add another user? [Y] for yes, else no");
							check = sin.nextLine().toUpperCase();
							if (check.equals("Y")) {
								System.out.println("Enter the username you'd like to share this account with.");
							} else {
								loop = false;
							}
						}
						loop = true;
					}
					break;
				case "5": // Give Account Access
					log.info("Chose Give Account Access");
					System.out.println();
					System.out.println("Would you like to give someone else access to account " + curUse.getActive().getID()
							+ "? [Y] for yes, anything else for no");
					check = sin.nextLine().toUpperCase();
					if (check.equals("Y")) {
						System.out.println("Enter username of user you'd like to give access to.");
						check = sin.nextLine();
						// Check if username in User table
						log.info("Checking if given user exists");
						if (userDAO.userExists(check)) {
							if(curUse.getActive().getType().equals("S")) {
								log.info("Changing account to Joint");
								curUse.getActive().setType("J");
								log.info("Updating in Database");
								acctDAO.updateAccount(curUse.getActive());
							}
							log.info("Updating Database to add new relation...");
							acctDAO.addUserAccess(curUse.getActive(), check);
						} else {
							System.out.println("User " + check + " does not exist.");
						}
					}
					break;
				case "6": // Logout
					log.info("Chose Logout");
					loop = false;
					break;
				default: // Invalid Command
					System.out.println("Invalid Command. Enter a number 1-6: ");
					propInput = false;
				}
			} while (propInput == false);
		}
	}

	// Employee:
	// Customers
	// Select Customer Info
	// Customer Username
	// Customer Password(?)
	// Customer Accounts
	// Account Info (Give Account Number)
	// Current Applications
	public void emplMenu(User curUse, Scanner sin) {
		log.info("Enters emplMenu method");
		String check;
		boolean propInput = true;
		boolean loop = true;

		while (loop) {
			System.out.println("EMPLOYEE MENU:");
			System.out.println();
			System.out.println("Enter the number of the action you want to perform: ");
			System.out.println("[1] Inspect Customers");
			System.out.println("[2] Inspect Applications");
			System.out.println("[3] Logout");
			System.out.println();

			do {
				propInput = true;
				check = sin.nextLine();
				switch (check) {
				case "1":// Customers
					log.info("Chose Customers");
					ArrayList<User> users = userDAO.allCustomers();
					if (!users.isEmpty()) {
						printUsers(users);
					} else {
						System.out.println("No Customers :(");
					}
					System.out.println("Press Enter to proceed");
					sin.nextLine();
					break;
				case "2":// Applications
					log.info("Chose Applications");
					approveDeny(sin);
					break;
				case "3":// Logout
					log.info("Chose Logout");
					loop = false;
					break;
				default:
					System.out.println("Invalid Command. Enter a number 1-3");
					propInput = false;
				}

			} while (propInput == false);
		}
	}

	// Admin:
	// Employee Menu
	// +
	// Customer Accounts
	// Account Info (Give Account Number)
	// Change Account Balance (Give Account Number)
	// Withdraw
	// Deposit
	// Transfer
	// Cancel Account (Give Account Number)
	public void admnMenu(User curUse, Scanner sin) {
		log.info("Enters admnMenu method");
		String check;
		boolean propInput = true;
		boolean loop = true;

		while (loop) {
			System.out.println("ADMIN MENU:");
			System.out.println();
			System.out.println("Enter the number of the action you want to perform: ");
			System.out.println("[1] Inspect Customers");
			System.out.println("[2] All Accounts");
			System.out.println("[3] Switch Accounts");
			System.out.println("[4] Account Actions");
			System.out.println("[5] Inspect Applications");
			System.out.println("[6] Logout");
			System.out.println();

			do {
				propInput = true;
				check = sin.nextLine();
				switch (check) {
				case "1":// Customers
					log.info("Chose Customers");
					ArrayList<User> users = userDAO.allCustomers();
					if (!users.isEmpty()) {
						printUsers(users);
					} else {
						System.out.println("No Customers :(");
					}
					System.out.println("Press Enter to proceed");
					sin.nextLine();
					break;
				case "2"://All Accounts
					log.info("Chose All Accounts");
					log.info("Grabbing all accounts from Database");
					ArrayList<Account> accounts = acctDAO.allAccounts();
					if(!accounts.isEmpty()) {
						printAccts(accounts);
					}
					else {
						System.out.println("No Accounts :(");
					}
					System.out.println("Press Enter to proceed");
					sin.nextLine();
					break;
				case "3"://Switch Accounts
					log.info("Chose Switch Accounts");
					switchAccount(sin, curUse);
					break;
				case "4"://Account Actions
					log.info("Chose Account Actions");
					log.info("Checks if User's getActive account");
					if (curUse.getActive() == null) {
						System.out.println("You have not chosen an account to alter yet, please Switch Accounts first.");
						break;
					} else if (!curUse.getActive().getStatus().equals("A")) {
						System.out.println("The chosen account is not approved for transactions.");
						break;
					}
					while (loop) {
						log.info("Enters Transaction Loop");
						System.out.println();
						System.out.println("Would you like to ");
						System.out.println("[1] Withdraw");
						System.out.println("[2] Deposit");
						System.out.println("[3] Transfer");
						System.out.println("[4] Cancel Account");
						System.out.println("[5] Exit Account Actions");
						System.out.println();

						do {
							propInput = true;
							check = sin.nextLine();
							switch (check) {
							case "1": // Withdraw
								log.info("Chose Withdraw");
								callAlter('W',curUse,sin);
								break;
							case "2": // Deposit
								log.info("Chose Deposit");
								callAlter('D',curUse,sin);
								break;
							case "3": // Transfer
								log.info("Chose Transfer");
								callAlter('T',curUse,sin);
								break;
							case "4": //Close Account
								log.info("Chose Close");
								log.info("Makes sure account is not already closed.");
								if(!curUse.getActive().getStatus().equals("C")) {
									System.out.println("Are you sure you want to close Account " + curUse.getActive().getID() + 
										"? [Y] for yes, else for no.");
									check = sin.nextLine().toUpperCase();
									if(check.equals("Y")) {
										curUse.getActive().setStatus("C");
										log.info("Updates account status in Database");
										acctDAO.updateAccount(curUse.getActive());
									}
								}
								else {
									System.out.println("Account is already closed.");
								}
								break;
							case "5": // Exit Act Trnsctn
								log.info("Chose Exit");
								loop = false;
								break;
							default: // Invalid Command
								System.out.println("Invalid Command. Enter a number 1-4: ");
								propInput = false;
							}
						} while (propInput == false);
						propInput = true;
						check = "1";
					}
					loop = true;
					break;
				case "5":// Applications
					log.info("Chose Applications");
					approveDeny(sin);
					break;
				case "6":// Logout
					log.info("Chose Logout");
					loop = false;
					break;
				default:
					System.out.println("Invalid Command. Enter a number 1-6");
					propInput = false;
				}

			} while (propInput == false);
		}

	}
}
