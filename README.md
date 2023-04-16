[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/mptE2SYx)
# Assignment 3: Build Automation and Mocking

1. ## JUnit + JMockit (25 points)
    In this assignment you will test a Java class UserAdmin by mocking an interface DBConnection.
    * You are expected to “fully” test the UserAdmin class; your tests should cover every line of code at least once and you should see every error message.
    * Test according to the specifications (JavaDoc and comments) in the source code.
    * Do NOT change the DBConnection, User or UserAdmin classes.
    * For this assignment you are graded on the overall coverage and quality of your JUnit tests.
    
    You can see your code coverage report by opening `build/reports/jacoco/test/html/index.html` in your browser.
    

1. ## Gradle Build File (25 points)
    Create a new gradle task at the bottom of the provided `build.gradle` file. The task you create should run
    1. Gradle `build` task
    1. Print `Build Finished!`
    
    The task you define should run the sub-tasks in that order. We will test your task by running
    `./gradlew -q myGradleTask` in the terminal.
 
