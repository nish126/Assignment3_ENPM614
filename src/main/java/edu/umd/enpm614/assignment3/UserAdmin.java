package edu.umd.enpm614.assignment3;

import java.sql.SQLException;
import java.util.List;

public class UserAdmin {
	private final DBConnection dbConn;
	
	public UserAdmin(DBConnection dbConn) {
		this.dbConn = dbConn;
	}

	public boolean createUser(String username, String password) {		
		try {
			// check whether username is taken
			if (dbConn.userExists(username)) {
				System.out.println("Error: username already exists in the database.");
				return false;
			}
			
			// add user to database
			dbConn.addUser(username, password);
			return true;
		} catch (SQLException sqle) {
			System.out.println("DBConnection problem at createUser().");
			return false;
		}
	}
	
	public boolean removeUser(String username) {
		try {
			// check whether user exists
			if (!dbConn.userExists(username)) {
				System.out.println("Error: user does NOT exist in the database.");
				return false;
			}
			
			if (dbConn.isAdmin(username)) {
				System.out.println("Error: cannot remove an administrator.");
				return false;
			}
			
			// delete user from database
			dbConn.deleteUser(username);
			return true;
		} catch (SQLException sqle) {
			System.out.println("DBConnection problem at removeUser().");
			return false;
		}
	}
	
	public void runUserReport() {
		try {
			List<User> users = dbConn.getUsers();
			
			if (users.isEmpty()) { // empty database
				System.out.println("No users in database...");
			} else if (users.size() <= 10) { // detailed reporting
				System.out.println("Listing all usernames:");
				for (User user : users) {
					System.out.println(user.getUsername());
				}
			} else { // summary reporting
				System.out.println("Total number of users: " + users.size());
				System.out.println(users.get(0).getUsername());
				System.out.println(users.get(1).getUsername());
				System.out.println(users.get(2).getUsername());
				System.out.println(users.get(3).getUsername());
				System.out.println(users.get(4).getUsername());
				System.out.println((users.size() - 5) + " more...");
			}
		} catch (SQLException sqle) {
			System.out.println("DBConnection problem at runUserReport().");
		}
	}
}
