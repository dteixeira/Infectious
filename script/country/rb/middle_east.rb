# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Middle East"
C.cells 				c, "middle_east.cell"
C.temperature			c, :hot
C.humidity				c, :arid
C.type					c, :rural
C.total_people			c, 516606503
C.number_airports		c, 2
C.number_ports			c, 1
C.number_hospitals		c, 2
C.airport_threshold		c, 0.5, 0.2
C.port_threshold		c, 0.5, 0.2
C.hospital_threshold	c, 0.5, 0.2
C.border_threshold		c, 0.5, 0.2
C.neighbour_names		c, ["North Africa", "Russia", "India", "China", "East Europe"]

# Add country to the application
C.add_country(c)