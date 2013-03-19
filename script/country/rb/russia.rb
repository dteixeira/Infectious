# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Russia"
C.cells 				c, "russia.cell"
C.temperature			c, :cold
C.humidity				c, :arid
C.type					c, :industrial
C.total_people			c, 144655534
C.number_airports		c, 2
C.number_ports			c, 2
C.number_hospitals		c, 2
C.airport_threshold		c, 0.5, 0.2
C.port_threshold		c, 0.5, 0.2
C.hospital_threshold	c, 0.5, 0.2
C.border_threshold		c, 0.5, 0.2
C.neighbour_names		c, ["East Europe", "Middle East", "China"]

# Add country to the application
C.add_country(c)