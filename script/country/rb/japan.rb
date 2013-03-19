# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Japan"
C.cells 				c, "japan.cell"
C.temperature			c, :temperate
C.humidity				c, :mediterranean
C.type					c, :industrial
C.total_people			c, 138433495
C.number_airports		c, 1
C.number_ports			c, 1
C.number_hospitals		c, 1
C.airport_threshold		c, 0.5, 0.2
C.port_threshold		c, 0.5, 0.2
C.hospital_threshold	c, 0.5, 0.2
C.border_threshold		c, 0.5, 0.2
C.neighbour_names		c, []

# Add country to the application
C.add_country(c)