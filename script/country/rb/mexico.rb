# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Mexico"
C.cells 				c, "mexico.cell"
C.temperature			c, :hot
C.humidity				c, :arid
C.type					c, :rural
C.total_people			c, 112722356
C.number_airports		c, 1
C.number_ports			c, 1
C.number_hospitals		c, 1
C.airport_threshold		c, 0.5, 0.2
C.port_threshold		c, 0.5, 0.2
C.hospital_threshold	c, 0.5, 0.2
C.border_threshold		c, 0.5, 0.2
C.neighbour_names		c, ["United States", "Peru"]

# Add country to the application
C.add_country(c)