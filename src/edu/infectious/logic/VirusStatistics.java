package edu.infectious.logic;

import java.util.HashMap;
import java.util.Map;

import edu.infectious.script.country.CountryClimateHumidity;
import edu.infectious.script.country.CountryClimateTemperature;

public abstract class VirusStatistics {

	private static final double CLIMATE_LEVEL_INCREMENT = 0.25;
	private static final int MAX_CLIMATE_LEVEL = 4;
	
	private static double notoriety = 0.0;
	private static double deadliness = 0.0;
	private static double infectiousness = 0.0;
	private static double notorietyDelta = 0.0;
	private static double deadlinessDelta = 0.0;
	private static double infectiousnessDelta = 0.0;
	private static boolean waterTransmission = false;
	private static boolean airTransmission = false;
	private static boolean plagueTransmission = false;
	private static boolean livestockTransmission = false;
	private static int waterTransmissionCost = 20;
	private static int airTransmissionCost = 20;
	private static int plagueTransmissionCost = 15;
	private static int livestockTransmissionCost = 15;
	private static int level1Cost = 3;
	private static int level2Cost = 5;
	private static int level3Cost = 8;
	private static int level4Cost = 12;
	private static Map<CountryClimateTemperature, Integer> temperatureBonus = null;
	private static Map<CountryClimateHumidity, Integer> humidityBonus = null;
	
	public static void initVirusStatistics() {
		notoriety = 0.0;
		deadliness = 0.0;
		infectiousness = 0.0;
		notorietyDelta = 0.0;
		deadlinessDelta = 0.0;
		infectiousnessDelta = 0.0;
		waterTransmission = false;
		airTransmission = false;
		plagueTransmission = false;
		livestockTransmission = false;
		temperatureBonus = new HashMap<CountryClimateTemperature, Integer>();
		temperatureBonus.put(CountryClimateTemperature.HOT, 0);
		temperatureBonus.put(CountryClimateTemperature.COLD, 0);
		temperatureBonus.put(CountryClimateTemperature.TEMPERATE, 0);
		humidityBonus = new HashMap<CountryClimateHumidity, Integer>();
		humidityBonus.put(CountryClimateHumidity.ARID, 0);
		humidityBonus.put(CountryClimateHumidity.MEDITERRANEAN, 0);
		humidityBonus.put(CountryClimateHumidity.TROPICAL, 0);
	}
	
	public static double getNotoriety() {
		return notoriety;
	}
	
	public static void setNotoriety(double notoriety) {
		VirusStatistics.notoriety = notoriety;
	}
	
	public static double getDeadliness() {
		return deadliness;
	}
	
	public static void setDeadliness(double deadliness) {
		VirusStatistics.deadliness = deadliness;
	}
	
	public static double getInfectiousness() {
		return infectiousness;
	}
	
	public static void setInfectiousness(double infectiousness) {
		VirusStatistics.infectiousness = infectiousness;
	}
	
	public static void applyDeadlinessVariation(double variation) {
		deadlinessDelta += variation;
	}
	
	public static void applyInfectiousnessVariation(double variation) {
		infectiousnessDelta += variation;
	}
	
	public static void applyNotorietyVariation(double variation) {
		notorietyDelta += variation;
	}
	
	public static void normalizeValues() {
		notoriety = Math.min(Math.max(notoriety, 0.0), 100.0);
		infectiousness = Math.min(Math.max(infectiousness, 0.0), 100.0);
		deadliness = Math.min(Math.max(deadliness, 0.0), 100.0);
	}

	public static boolean isWaterTransmission() {
		return waterTransmission;
	}

	public static void setWaterTransmission(boolean waterTransmission) {
		VirusStatistics.waterTransmission = waterTransmission;
	}

	public static boolean isAirTransmission() {
		return airTransmission;
	}

	public static void setAirTransmission(boolean airTransmission) {
		VirusStatistics.airTransmission = airTransmission;
	}

	public static boolean isPlagueTransmission() {
		return plagueTransmission;
	}

	public static void setPlagueTransmission(boolean plagueTransmission) {
		VirusStatistics.plagueTransmission = plagueTransmission;
	}

	public static boolean isLivestockTransmission() {
		return livestockTransmission;
	}

	public static void setLivestockTransmission(boolean livestockTransmission) {
		VirusStatistics.livestockTransmission = livestockTransmission;
	}
	
	public static double getTemperatureBonus(CountryClimateTemperature temperature) {
		return temperatureBonus.get(temperature) * CLIMATE_LEVEL_INCREMENT;
	}
	
	public static double getHumidityBonus(CountryClimateHumidity humidity) {
		return humidityBonus.get(humidity) * CLIMATE_LEVEL_INCREMENT;
	}
	
	public static boolean isMaxTemperatureLevel(CountryClimateTemperature temperature) {
		return temperatureBonus.get(temperature) == MAX_CLIMATE_LEVEL;
	}
	
	public static boolean isMaxHumidityLevel(CountryClimateHumidity humidity) {
		return humidityBonus.get(humidity) == MAX_CLIMATE_LEVEL;
	}
	
	public static int getTemperatureLevel(CountryClimateTemperature temperature) {
		return temperatureBonus.get(temperature);
	}
	
	public static int getHumidityLevel(CountryClimateHumidity humidity) {
		return humidityBonus.get(humidity);
	}
	
	public static int setTemperatureLevel(CountryClimateTemperature temperature, int level) {
		return temperatureBonus.put(temperature, level);
	}
	
	public static int setHumidityLevel(CountryClimateHumidity humidity, int level) {
		return humidityBonus.put(humidity, level);
	}
	
	public static void incrementTemperatureBonus(CountryClimateTemperature temperature) {
		temperatureBonus.put(temperature, Math.min(temperatureBonus.get(temperature) + 1, MAX_CLIMATE_LEVEL));
	}
	
	public static void incrementHumidityBonus(CountryClimateHumidity humidity) {
		humidityBonus.put(humidity, Math.min(humidityBonus.get(humidity) + 1, MAX_CLIMATE_LEVEL));
	}

	public static int getWaterTransmissionCost() {
		return waterTransmissionCost;
	}

	public static int getAirTransmissionCost() {
		return airTransmissionCost;
	}

	public static int getPlagueTransmissionCost() {
		return plagueTransmissionCost;
	}

	public static int getLivestockTransmissionCost() {
		return livestockTransmissionCost;
	}

	public static int getLevel1Cost() {
		return level1Cost;
	}

	public static void setLevel1Cost(int level1Cost) {
		VirusStatistics.level1Cost = level1Cost;
	}

	public static int getLevel2Cost() {
		return level2Cost;
	}

	public static void setLevel2Cost(int level2Cost) {
		VirusStatistics.level2Cost = level2Cost;
	}

	public static int getLevel3Cost() {
		return level3Cost;
	}

	public static void setLevel3Cost(int level3Cost) {
		VirusStatistics.level3Cost = level3Cost;
	}

	public static int getLevel4Cost() {
		return level4Cost;
	}

	public static void setLevel4Cost(int level4Cost) {
		VirusStatistics.level4Cost = level4Cost;
	}

	public static double getNotorietyDelta() {
		return notorietyDelta;
	}

	public static void setNotorietyDelta(double notorietyDelta) {
		VirusStatistics.notorietyDelta = notorietyDelta;
	}

	public static double getDeadlinessDelta() {
		return deadlinessDelta;
	}

	public static void setDeadlinessDelta(double deadlinessDelta) {
		VirusStatistics.deadlinessDelta = deadlinessDelta;
	}

	public static double getInfectiousnessDelta() {
		return infectiousnessDelta;
	}

	public static void setInfectiousnessDelta(double infectiousnessDelta) {
		VirusStatistics.infectiousnessDelta = infectiousnessDelta;
	}

}
