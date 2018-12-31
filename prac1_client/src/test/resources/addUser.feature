Feature: Add user
	Scenario: Adding a new user in database
		Given I open Firefox web browser and launch the application
		And I have a new user <User>
		And I have a admin user <UserA>
		Then Get to User Management Index
		When Save the new user <User>
		Then Validate new <User>