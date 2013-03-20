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
C.thresholds			c, 0.3, 0.1
C.neighbour_names		c, ["United States"]

# Add country to the application
C.add_country(c)