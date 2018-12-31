Feature: Modify pelicula
	Scenario: Modifing a film in database
		Given I open Firefox web browser and launch the application
		Given I have a film <Pelicula>
		When User logs in <UserA>
		Then Get to Movie Management Index
		When Modify the film <Pelicula>
		Then Validate <Pelicula>