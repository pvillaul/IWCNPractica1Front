Feature: Modify user
	Scenario: Modifing a user in database
	Given I have a user <User
	When Modify the user <User>
	Then Returns <User>
