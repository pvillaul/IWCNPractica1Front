Feature: Delete user
	Scenario: Deleting a user in database
		Given I open Firefox web browser and launch the application
		And I have a username <String>
		And I have a admin user <UserA>
		When User logs in <UserA>
		Then Get to User Management Index
		When Delete the user that matches the username <String>
		Then Validate Delete <User>