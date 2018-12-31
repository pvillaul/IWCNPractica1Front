Feature: Modify user
	Scenario: Modifing a user in database
		Given I open Firefox web browser and launch the application
		Given I have a username <String>
		Then Get to User Management Index
		When Modify the user <User>
		Then Validate <User>