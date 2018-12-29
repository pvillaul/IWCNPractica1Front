Feature: Delete user
	Scenario: Deleting a user in database
	Given I have a user <User>
	When Delete the user <User>
	Then Returns <Null>
