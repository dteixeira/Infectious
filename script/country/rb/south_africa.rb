# Create country object
c = C.init_country

# Define country variables
C.name 					c, "South Africa"
C.cells 				c, "south_africa.cell"
C.temperature			c, :hot
C.humidity				c, :arid
C.type					c, :industrial
C.total_people			c, 86970564
C.number_airports		c, 2
C.number_ports			c, 1
C.number_hospitals		c, 1
C.thresholds			c, 0.4, 0.3
C.neighbour_names		c, ["North Africa"]

# Add country to the application
C.add_country(c)