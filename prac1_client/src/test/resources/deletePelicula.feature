Feature: Delete pelicula
	Scenario: Deleting a film in database
	Given I have a film <Pelicula>
	When Delete the film <Pelicula>
	Then Returns <Null>
