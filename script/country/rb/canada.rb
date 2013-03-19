# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Canada"
C.cells 				c, "canada.cell"
C.temperature			c, :cold
C.humidity				c, :mediterranean
C.type					c, :industrial
C.total_people			c, 34432967
C.number_airports		c, 2
C.number_ports			c, 2
C.number_hospitals		c, 2
C.airport_threshold		c, 0.5, 0.2
C.port_threshold		c, 0.5, 0.2
C.hospital_threshold	c, 0.5, 0.2
C.border_threshold		c, 0.5, 0.2
C.neighbour_names		c, ["United States"]

# Add country to the application
C.add_country(c)