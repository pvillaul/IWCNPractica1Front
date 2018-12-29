Feature: Modify pelicula
	Scenario: Modifing a film in database
	Given I have a film <Pelicula>
	When Modify the film <Pelicula>
	Then Returns <Pelicula>
