Feature: Delete pelicula
	Scenario: Deleting a film in database
		Given I open Firefox web browser and launch the application
		And I have a movie <Pelicula>
		And I have a admin user <UserA>
		Then Get to Movie Management Index
		When Delete the Movie <Pelicula>
		Then Validate delete <Pelicula>