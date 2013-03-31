package edu.infectious.script.country;

import java.io.Serializable;

public class CountryThreshold implements Serializable {

	/*
	 * Constants
	 */
	private static final long	serialVersionUID	= 1L;

	/*
	 * Instance fields
	 */
	private double				deadThreshold;
	private double				infectedThreshold;

	/*
	 * Constructor
	 */
	public CountryThreshold(double infectedThreshold, double deadThreshold) {
		this.infectedThreshold = infectedThreshold;
		this.deadThreshold = deadThreshold;
	}

	/*
	 * Instance methods
	 */
	public double getDeadThreshold() {
		return deadThreshold;
	}

	public double getInfectedThreshold() {
		return infectedThreshold;
	}

	public void setDeadThreshold(double deadThreshold) {
		this.deadThreshold = deadThreshold;
	}

	public void setInfectedThreshold(double infectedThreshold) {
		this.infectedThreshold = infectedThreshold;
	}

}
