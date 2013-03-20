# Create country object
c = C.init_country

# Define country variables
C.name 					c, "Brazil"
C.cells 				c, "brazil.cell"
C.temperature			c, :hot
C.humidity				c, :tropical
C.type					c, :industrial
C.total_people			c, 201131791
C.number_airports		c, 1
C.number_ports			c, 1
C.number_hospitals		c, 1
C.thresholds			c, 0.4, 0.3
C.neighbour_names		c, ["Peru", "Argentina"]

# Add country to the application
C.add_country(c)