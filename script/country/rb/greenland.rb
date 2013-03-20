# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Greenland"
C.cells 				c, "greenland.cell"
C.temperature			c, :cold
C.humidity				c, :arid
C.type					c, :rural
C.total_people			c, 56749
C.number_airports		c, 1
C.number_ports			c, 1
C.number_hospitals		c, 1
C.thresholds			c, 0.4, 0.3
C.neighbour_names		c, []

# Add country to the application
C.add_country(c)