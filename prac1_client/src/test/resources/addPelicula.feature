Feature: Add pelicula
	Scenario: Adding a new film in database
	Given I have a film <Pelicula>
	When Save the film <Pelicula>
	Then Returns <Pelicula>
