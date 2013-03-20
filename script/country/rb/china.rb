# Create country object
c = C.init_country

# Define country variables
C.name 					c, "China"
C.cells 				c, "china.cell"
C.temperature			c, :temperate
C.humidity				c, :arid
C.type					c, :industrial
C.total_people			c, 1553375130
C.number_airports		c, 2
C.number_ports			c, 1
C.number_hospitals		c, 2
C.thresholds			c, 0.4, 0.2
C.neighbour_names		c, ["Russia", "Middle East", "India"]

# Add country to the application
C.add_country(c)