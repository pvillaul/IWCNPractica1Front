Feature: Modify user
	Scenario: Log in as a User in the application
		Given I open Firefox web browser and launch the application
		And I have a admin user <UserA>
		When User logs in <UserA>
		Then Validate log in <UserA>