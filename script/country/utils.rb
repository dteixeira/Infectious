def init_country
	Java::edu::infectious::script::country.Country.new
end

def init_name country, name
	country.setName(name);
end

def init_color country, red, green, blue
	country.setCountryColor(Java::java::awt.Color.new(red, green, blue, 0.2))
end

def init_cells country, filename
	list = Java::java::util.ArrayList.new;
	file = Java::java::io.File.new(filename)
	scanner = Java::java::util.Scanner.new(file)
	total = scanner.nextInt()
	(0...total).each do |i|
		point = Java::java::awt.Point.new(scanner.nextInt(), scanner.nextInt())
		list.add(point)
	end
	country.bind_cells(list)
end

def add_country country
	Java::edu::infectious::script::country.Country.getCountryList().add(country)
end