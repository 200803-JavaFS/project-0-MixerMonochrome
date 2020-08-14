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

	// Method to login
	public User login(Scanner sin) {
		// System.out.println("Gets to Log In!");
		String potentUse = "";
		String potentPass = "";
		boolean propInput;
		User dudeGuy = new User();
		do { // loop until a correct username/password combo is entered.
			propInput = true;
			System.out.print("Enter Username: ");
			potentUse = sin.nextLine();
			System.out.print("Enter Password: ");
			potentPass = sin.nextLine();
			if(userDAO.userExists(potentUse) && userDAO.checkPass(potentUse, potentPass)){
				dudeGuy = userDAO.findUserByName(potentUse);
			}
			else {
				System.out.println("Your username or password was incorrect, please try again.");
				System.out.println("(Remember that your information is case-sensitive)");
				propInput = false;
			}
		} while (propInput == false);
		return dudeGuy;
	}

	// Method to signup
	public User signup(Scanner sin) {
		String potentUse = "";
		String potentPass = "";
		String fName = "";
		String lName = "";
		String phoneNum = "";
		String address = "";
		
		boolean propInput;
		do { // loop until an unused username is entered.
			propInput = true;
			System.out.print("Please enter the username you would like (case-sensitive): ");
			potentUse = sin.nextLine();
			if(userDAO.userExists(potentUse)) {
				propInput = false;
				System.out.println("Sorry, the username " + potentUse + " is already in use. Please select a different name.");
			}
		} while (propInput == false);
		System.out.print("Please enter your preferred password (case-sensitive): ");
		potentPass = sin.nextLine();
		// Double check maybe?

		// Pass potentUse and potentPass to User table and add a new row
		User newbie = new User(potentUse, "C", fName, lName, phoneNum, address);
		userDAO.createUser(newbie, potentPass);
		return newbie;
	}

	// Method to Logout

	// Changes the balance of a given account based on user u's active account
	// based on their permissions
	public boolean alterBalance(char action, int amount, User curUse) {
		Account a = curUse.getActive();
		switch (action) {
		case 'W':
			if (amount < 0) {
				System.out.println("Can't withdraw a negative amount");
				return false;
			} else if (amount > curUse.getActive().getBalance()) {
				System.out.println("Can't withdraw more money than exists in account");
				return false;
			}
			a.setBalance(a.getBalance() - amount);
			acctDAO.updateAccount(a);
			break;
		case 'D':
			if (amount < 0) {
				System.out.println("Can't deposit a negative amount");
			}
			a.setBalance(a.getBalance() + amount);
			acctDAO.updateAccount(a);
			break;
		default:
			return false;
		}

		return true;
	}

	private void printAccts(User curUse) {
		ArrayList<Account> bleh = acctDAO.getAccountsByUser(curUse.getName());
		System.out.println();
		for (Account a : bleh) {
			a.toString();
		}
		System.out.println();
	}

	private void printUsers(ArrayList<User> customers) {
		System.out.println();
		for (User u : customers) {
			u.toString();
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
		String check = "";
		boolean propInput = true;
		boolean loop = true;
		int amount = 0;
		Account to = null;

		while (loop) {
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
					while (loop) {
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
							sw: switch (check) {
							case "1": // Withdraw
								System.out.println("How much would you like to withdraw?");
								try {
									amount = Integer.parseInt(sin.nextLine());
								} catch (NumberFormatException e) {
									System.out.println("Invalid amount, please use only numbers");
									break sw;
								}
								alterBalance('W', amount, curUse);
								break;
							case "2": // Deposit
								System.out.println("How much would you like to deposit?");
								try {
									amount = Integer.parseInt(sin.nextLine());
								} catch (NumberFormatException e) {
									System.out.println("Invalid amount, please use only numbers");
									break sw;
								}
								alterBalance('D', amount, curUse);
								break;
							case "3": // Transfer
								System.out.println("How much would you like to transfer?");
								try {
									amount = Integer.parseInt(sin.nextLine());
								} catch (NumberFormatException e) {
									System.out.println("Invalid amount, please use only numbers");
									break sw;
								}
								System.out.println("Account number you are transferring to");
								check = sin.nextLine();
								if (acctDAO.accountExists(check)) {
									to = acctDAO.getAccountByID(check);
									// Pull money from current account
									alterBalance('W', amount, curUse);
									// Save current account
									Account from = curUse.getActive();
									// Change to target account
									curUse.setActive(to);
									// Deposit to target account
									alterBalance('D', amount, curUse);
									// Revert to original account
									curUse.setActive(from);
								}
								else {
									System.out.println("Account number " + check + " does not exist");
								}
								break;
							case "4": // Exit Act Trnsctn
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
					System.out.println();
					System.out.println("Accounts you have access to");
					printAccts(curUse);
					break;
				case "3": // Switch Accounts
					System.out.println();
					System.out.println("What account would you like to switch to?");
					check = sin.nextLine();
					if (curUse.getAccts().contains(check)) {
						curUse.setActive(acctDAO.getAccountByID(check));
					} else {
						System.out.println("You don't have access to this account");
					}
					check = "3";

					break;
				case "4": // New Accounts
					// Ask if solo or joint account
					// Place new acct in Account table with closed status and balance 0
					break;
				case "5": // Give Account Access
					System.out.println();
					System.out.println("Would you like to give someone else access to account " + curUse.getActive()
							+ "? [Y] for yes, anything else for no");
					check = sin.nextLine().toUpperCase();
					if (check.equals("Y")) {
						System.out.println("Enter username of user you'd like to give access to.");
						check = sin.nextLine();
						// Check if username in User table
						if(userDAO.userExists(check)) {
							acctDAO.addUserAccess(curUse.getActive(), check);
						}
						else {
							System.out.println("User " + check + " does not exist.");
						}
					}
					break;
				case "6": // Logout
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
		String check = "";
		boolean propInput = true;
		boolean loop = true;
		ArrayList<User> customers = null;

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
					customers = userDAO.allUsers();
					while (loop) {
						printUsers(customers);
						System.out.println("Select Customer (enter E to go back)");
						do {
							propInput = true;
							check = sin.nextLine();
							if (check.toUpperCase() == "E") {
								loop = false;
							} else if (userDAO.userExists(check)) {

							}

						} while (propInput == false);
					}
					break;
				case "2":// Applications
					break;
				case "3":// Logout
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

	}
}
