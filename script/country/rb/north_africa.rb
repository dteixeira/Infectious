# Create country object
c = C.init_country

# Define country variables
C.name 					c, "North Africa"
C.cells 				c, "north_africa.cell"
C.temperature			c, :hot
C.humidity				c, :arid
C.type					c, :rural
C.total_people			c, 695039229
C.number_airports		c, 1
C.number_ports			c, 1
C.number_hospitals		c, 1
C.thresholds			c, 0.6, 0.4
C.neighbour_names		c, ["South Africa", "Middle East"]

# Add country to the application
C.add_country(c)