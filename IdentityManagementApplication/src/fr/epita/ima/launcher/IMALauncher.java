/**
 * 
 */
package fr.epita.ima.launcher;

import java.util.Scanner;

import fr.epita.ima.business.ManageIdentity;
import fr.epita.ima.datamodel.Identity;


/**
 * This is a Identity management launcher. Launcher helps to provide a console based user interface to facilitate the access to the database.
 * Various CRUD functions are provided for the data manipulations.
 * Note : The application is case sensitive. Please insert correct inputs.
 * 
 * @author Neha
 * 
 */

public class IMALauncher {

	/**
	 * This is the main method. It displays the login page information and
	 * accepts user input.
	 * It provides the console to the application.
	 * 
	 * @return void
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to the IMA Software");
		Scanner scan = new Scanner(System.in);

		/*
		 * If the method 'authenticate returns false, this block will be
		 * executed. It will end the application.
		 */
		if (!authenticate(scan)) {
			System.out.println("Thank you for using the application, Bye!");
			scan.close();

		} else {
			/*
			 * If the authentication is successful, the application menu shall be displayed.
			 */
			while (true) {
				
				System.out.println("Please select an action :");
				System.out.println("A. Create an Identity");
				System.out.println("B. Update an Identity");
				System.out.println("C. Delete an Identity");
				System.out.println("D. List all Identities");
				System.out.println("Q. Quit");
				
				
				String menuSelection = scan.next();
				
				//Orcastration of the application based on the user input.
				switch (menuSelection) {
				case "A":
					// Create an Identity
					createIdentity(scan);
					break;

				case "B": 
					// Update an Identity
					updateIdentity(scan);
					break;

				case "C":
					// Delete an Identity
					deleteIdentity(scan);
					break;
				case "D":
					// List all identities
					ManageIdentity manage = new ManageIdentity();
					manage.listAll();
					break;

					
				case "Q":
					// Quit. Return to the authentication page
					System.out.println("Thank you for using the application. Bye!");
					break;

				default: 
					// Display message and return to the menu
					System.out.println("Invalid Selection. Select again");

				}
				
				System.out.println("Do you want to continue?");
				System.out.println("Y for Yes; N for No");
				String val = scan.next();
				if (val.equals("N")) {
					System.out.println("Thank you for using the application. Bye!");
					break;

				} else {
					continue;
				}
			}
		}
		scan.close();
	}



	/**
	 * Accepts user inputs and calls the business methods to create Identity.
	 * 
	 * @param scan Used to accept user input.
	 */
	private static void createIdentity(Scanner scan) {
		System.out.println("Please enter the name");
		String name = scan.next();
		System.out.println("Please enter the email address");
		String email = scan.next();
		System.out.println("Please enter the birth date (yyyy-mm-dd)");
		String bdate = scan.next();
		ManageIdentity manage = new ManageIdentity();
		
		manage.create(name,email,bdate);
	}

	/**
	 * Accepts user inputs and calls the business methods to delete Identity.
	 * 
	 * @param scan Used to accept user input.
	 */
	private static void deleteIdentity(Scanner scan) {
		System.out.println("Please enter an existing id");
		int id = scan.nextInt();
		ManageIdentity manage = new ManageIdentity();
		manage.delete(id);
	}
	/**
	 * Accepts user inputs and calls the business methods to update Identity.
	 * This method displays the current values and then accepts inputs to update.
	 * 
	 * @param scan Used to accept user input.
	 */
	private static void updateIdentity(Scanner scan) {
		System.out.println("Please enter an existing id");
		int id = scan.nextInt();
		ManageIdentity manage = new ManageIdentity();
		Identity identity = manage.selectIdentity(id);
		if(identity!=null){
			System.out.println("Current values for id "+identity.getId()+" :");
			System.out.println("Name :"+identity.getName());
			System.out.println("Email :"+identity.getEmail());
			System.out.println("Birthdate :"+identity.getBdate());
			
			System.out.println("Please enter new value for Name : ");
			String name = scan.next();
			identity.setName(name);
			
			System.out.println("Please enter new value for Email : ");
			String email = scan.next();
			identity.setEmail(email);
			
			System.out.println("Please enter new value for Birthdate (YYYY-MM-DD) : ");
			String bdate = scan.next();
			identity.setBdate(bdate);
			
			manage.update(identity);
		}
	}



	/**
	 * This method accepts username and password and authenticates the user.
	 * 
	 * @return boolean
	 */
	private static boolean authenticate(Scanner scan) {
		System.out.println("Please enter the username");
		String username = scan.next();

		System.out.println("Please enter the password");
		String passwd = scan.next();

		if (username.equals("admin") && passwd.equals("passwd")) {
			System.out.println("Authentication successful.");
			return true;
		} else {
			System.out.println("Invalid username or password. Authentication failed");
			return false;
		}
	}


	
}
