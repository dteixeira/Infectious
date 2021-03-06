# Create country object
c = C.init_country

# Define country variables
C.name 					c, "East Europe"
C.cells 				c, "east_europe.cell"
C.temperature			c, :cold
C.humidity				c, :mediterranean
C.type					c, :industrial
C.total_people			c, 330062403
C.number_airports		c, 1
C.number_ports			c, 1
C.number_hospitals		c, 1
C.thresholds			c, 0.3, 0.1
C.neighbour_names		c, ["West Europe"]

# Add country to the application
C.add_country(c)