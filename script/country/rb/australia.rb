# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Australia"
C.cells 				c, "australia.cell"
C.temperature			c, :hot
C.humidity				c, :arid
C.type					c, :industrial
C.total_people			c, 23233456
C.number_airports		c, 1
C.number_ports			c, 1
C.number_hospitals		c, 1
C.thresholds			c, 0.3, 0.2
C.neighbour_names		c, []

# Add country to the application
C.add_country(c)