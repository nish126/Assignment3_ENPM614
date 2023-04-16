package edu.umd.enpm614.assignment3;

import java.sql.SQLException;
import java.util.List;

public interface DBConnection {
	/**
	 * checks whether a user with the given username exists in the database
	 * 
	 * @param username user's username
	 * @return true if the user exists, false if otherwise
	 * @throws SQLException
	 */
	public boolean userExists(String username) throws SQLException;
	
	/**
	 * adds a new user to the database with the given username and password
	 * 
	 * @param username user's username
	 * @param password user's password
	 * 
	 * @throws SQLException
	 */
	public void addUser(String username, String password) throws SQLException;
	
	/**
	 * deletes an existing user with the given username from the database
	 * 
	 * @param username user's username
	 * 
	 * @throws SQLException
	 */
	public void deleteUser(String username) throws SQLException;
	
	/**
	 * checks whether a user with the given username is an administrator
	 * 
	 * @param username user's username
	 * @return true if the user is an administrator, false if otherwise
	 * @throws SQLException
	 */
	public boolean isAdmin(String username) throws SQLException;
	
	/**
	 * returns a list of all users currently in the database
	 * 
	 * @return list of users
	 * @throws SQLException
	 */
	public List<User> getUsers() throws SQLException;
}

