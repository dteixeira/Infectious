require 'java'

module CModule

	# Java imported packages
	include_package 'edu.infectious.script.country'
	include_package 'java.awt'
	include_package 'java.io'
	include_package 'java.util'
	
	def self.included(base)
       	base.extend(ClassMethods)
    end
	
	# Utility methods for country creation
	module ClassMethods
	
		def init_country
			CModule::Country.new
		end
		
		def name country, name
			country.setName(name);
		end
		
		def cells country, filename
			list = CModule::ArrayList.new;
			file = CModule::File.new("script/country/cell/#{filename}")
			scanner = CModule::Scanner.new(file)
			total = scanner.nextInt()
			(0...total).each do |i|
				point = CModule::Point.new(scanner.nextInt(), scanner.nextInt())
				list.add(point)
			end
			country.bind_cells(list)
			scanner.close
		end
		
		def temperature country, temperature
			country.setTemperature(CModule::CountryClimateTemperature.valueOf(temperature.to_s.upcase))
		end
		
		def humidity country, humidity
			country.setHumidity(CModule::CountryClimateHumidity.valueOf(humidity.to_s.upcase))
		end
		
		def type country, type
			country.setType(CModule::CountryType.valueOf(type.to_s.upcase))
		end
		
		def total_people country, people
			country.setTotalPeople(people)
			country.setAlivePeople(people)
		end
		
		def number_ports country, ports
			country.setnPorts(ports)
		end
	
		def number_airports country, ports
			country.setnAirports(ports)
		end
		
		def number_hospitals country, hospitals
			country.setnHospitals(hospitals)
		end

		def thresholds country, inf, dead
			country.setThresholds(CModule::CountryThreshold.new(inf, dead))
		end
		
		def neighbour_names country, names
			country.setNeighbourNames(names)
		end
		
		def add_country country
			CModule::Country.getCountryList().add(country)
		end
	end
end

# Base call class
class C
	include CModule
end