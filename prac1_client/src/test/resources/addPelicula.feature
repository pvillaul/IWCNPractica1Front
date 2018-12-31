Feature: Add pelicula
	Scenario: Adding a new film in database
		Given I open Firefox web browser and launch the application
		And I have a movie <Pelicula>
		And I have a admin user <UserA>
		Then Get to Movie Management Index
		When Save the movie <Pelicula>
		Then Validate modify <Pelicula>