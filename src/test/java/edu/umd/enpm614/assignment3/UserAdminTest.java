package edu.umd.enpm614.assignment3;

import mockit.Mocked;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mockit.Expectations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;

class UserAdminTest {
    UserAdmin userAdmin;

    @Mocked
    DBConnection dbConnection;
    @BeforeEach
    public void setUp() {
        userAdmin = new UserAdmin(dbConnection);
    }

    @AfterEach
    public void tearDown() {
        userAdmin = null;
    }


    @Test
    void createUser(){
    userAdmin.createUser("something", "something");
        
    }
    
     //Testing username that already exists in the database
    @Test
    public void testCreateUserWhenUsernameIsTaken() throws SQLException {
        new Expectations() {{
            dbConnection.userExists("user1");
            result = true;
        }};
        assertFalse(userAdmin.createUser("user1", "pass1"));
    }
    //Testing creation of user where username that does not exist in the database
    @Test
    public void testCreateUserWhenUsernameIsNotTaken() throws SQLException {
        new Expectations() {{
            dbConnection.userExists("user2");
            result = false;

            dbConnection.addUser("user2", "pass2");
        }};
        assertTrue(userAdmin.createUser("user2", "pass2"));
    }

    //Testing creation of user when CreateUser method throws sql exception
    @Test
    public void testCreateUserWhenDBConnectionThrowsSQLException() throws SQLException {
        new Expectations() {{
            dbConnection.userExists("user3");
            result = false;

            dbConnection.addUser("user3", "pass3");
            result = new SQLException();
        }};
        assertFalse(userAdmin.createUser("user3", "pass3"));
    }

    //Testing removeUser() when a certain user does not exists in database
    @Test
    public void testRemoveUserWhenUserDoesNotExist() throws SQLException {
        new Expectations() {{
            dbConnection.userExists("user4");
            result = false;
        }};
        assertFalse(userAdmin.removeUser("user4"));
    }

    //Testing removeUser() when a certain user is admin
    @Test
    public void testRemoveUserWhenUserIsAdmin() throws SQLException {
        new Expectations() {{
            dbConnection.userExists("admin1");
            result = true;

            dbConnection.isAdmin("admin1");
            result = true;
        }};
        assertFalse(userAdmin.removeUser("admin1"));
    }
      //Testing removeUser() when a certain user is not admin and exists in database
    @Test
    public void testRemoveUserWhenUserIsNotAdmin() throws SQLException {
        new Expectations() {{
            dbConnection.userExists("user5");
            result = true;

            dbConnection.isAdmin("user5");
            result = false;

            dbConnection.deleteUser("user5");
        }};
        assertTrue(userAdmin.removeUser("user5"));
    }
      //Testing removeUser() for SQLException
    @Test
    public void testRemoveUserWhenDBConnectionThrowsSQLException() throws SQLException {
        new Expectations() {{
            dbConnection.userExists("user6");
            result = true;

            dbConnection.isAdmin("user6");
            result = false;

            dbConnection.deleteUser("user6");
            result = new SQLException();
        }};
        assertFalse(userAdmin.removeUser("user6"));
    }

    @Test
    public void testRunUserReportWhenEmptyDatabase() throws SQLException {
        new Expectations() {{
            dbConnection.getUsers();
            result = new ArrayList<User>();
        }};
        userAdmin.runUserReport();
    }
    //Testing runUserReport() when there is less than or equal 10 users in the database
    @Test
	public void testRunUserReportDetailedReporting() throws Exception {

		final User user1 = new User("user1","pass1");
		final User user2 = new User("user2","pass2");
		final User user3 = new User("user3","pass3");
		final User user4 = new User("user4","pass4");
		final User user5 = new User("user5","pass5");
		final User user6 = new User("user6","pass6");
		final User user7 = new User("user7","pass7");
		final User user8 = new User("user8","pass8");
		final User user9 = new User("user9","pass9");
		final User user10 = new User("user10","pass10");
		
		new Expectations() {
            {
			dbConnection.getUsers(); 
            result = java.util.Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8,user9, user10);
        }
        };

        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outputStream));
        userAdmin.runUserReport();
        
        System.setOut(new PrintStream(outputStream));

        System.setOut(stdout);
        String expectedOutput = "Listing all usernames:\r\nuser1\r\nuser2\r\nuser3\r\nuser4\r\nuser5\r\nuser6\r\nuser7\r\nuser8\r\nuser9\r\nuser10\r\n";
        
        
        assertEquals(expectedOutput, outputStream.toString());
	System.out.println(outputStream.toString());
    } 

    //Testing runUserReport() when there is greater than 10 users in the database
    @Test
	public void testRunUserReportSummaryReporting() throws Exception {

		final User user1 = new User("user1","pass1");
		final User user2 = new User("user2","pass2");
		final User user3 = new User("user3","pass3");
		final User user4 = new User("user4","pass4");
		final User user5 = new User("user5","pass5");
		final User user6 = new User("user6","pass6");
		final User user7 = new User("user7","pass7");
		final User user8 = new User("user8","pass8");
		final User user9 = new User("user9","pass9");
		final User user10 = new User("user10","pass10");
		final User user11 = new User("user11","pass11");
		
		new Expectations() {
            {
			dbConnection.getUsers(); 
            result = java.util.Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8,user9, user10, user11);
            }
        };

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outputStream));
        userAdmin.runUserReport();
        System.setOut(new PrintStream(outputStream));

        System.setOut(stdout);
        
        String expectedOutput = "Total number of users: 11\r\nuser1\r\nuser2\r\nuser3\r\nuser4\r\nuser5\r\n6 more...\r\n";
        
        assertEquals(expectedOutput, outputStream.toString());
	System.out.println(outputStream.toString());
    }  
    //Testing runUserReport() for SQLException
    @Test
    public void testRunUserReportThrowsSQLException() throws SQLException {
        new Expectations() {{
            dbConnection.getUsers();
            result = new SQLException();
        }};
        userAdmin.runUserReport();
    }
   
    @Test
    void removeUser() {
    }

    @Test
    void runUserReport() {
    }
}
