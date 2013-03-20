# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Indonesia"
C.cells 				c, "indonesia.cell"
C.temperature			c, :hot
C.humidity				c, :tropical
C.type					c, :rural
C.total_people			c, 357693390
C.number_airports		c, 2
C.number_ports			c, 1
C.number_hospitals		c, 1
C.thresholds			c, 0.5, 0.3
C.neighbour_names		c, []

# Add country to the application
C.add_country(c)