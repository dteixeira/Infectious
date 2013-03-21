package edu.infectious.logic;

public abstract class VirusStatistics {
	
	private static double notoriety = 0.0;
	private static double deadliness = 0.0;
	private static double infectiousness = 0.0;
	
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

}
