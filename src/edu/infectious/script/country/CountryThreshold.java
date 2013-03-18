package edu.infectious.script.country;

public class CountryThreshold {
	
	private double infectedThreshold;
	private double deadThreshold;
	
	public CountryThreshold(double infectedThreshold, double deadThreshold) {
		this.infectedThreshold = infectedThreshold;
		this.deadThreshold = deadThreshold;
	}

	public double getInfectedThreshold() {
		return infectedThreshold;
	}

	public void setInfectedThreshold(double infectedThreshold) {
		this.infectedThreshold = infectedThreshold;
	}

	public double getDeadThreshold() {
		return deadThreshold;
	}

	public void setDeadThreshold(double deadThreshold) {
		this.deadThreshold = deadThreshold;
	}

}
