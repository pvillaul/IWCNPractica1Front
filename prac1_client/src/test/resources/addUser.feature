Feature: Add user
	Scenario: Adding a new user in database
	Given I have a user <User>
	When Save the user <User>
	Then Returns <User>
