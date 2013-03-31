package edu.infectious.logic;

import java.io.Serializable;

public class SlavePackage implements Serializable {

	/*
	 * Constants
	 */
	private static final long	serialVersionUID	= 1L;

	/*
	 * Instance fields
	 */
	private double				deadlinessDelta		= 0.0;
	private double				infectiousnessDelta	= 0.0;
	private double				notorietyDelta		= 0.0;

	/*
	 * Constructor
	 */
	public SlavePackage() {}

	/*
	 * Instance methods
	 */
	public double getDeadlinessDelta() {
		return deadlinessDelta;
	}

	public double getInfectiousnessDelta() {
		return infectiousnessDelta;
	}

	public double getNotorietyDelta() {
		return notorietyDelta;
	}

	public void setDeadlinessDelta(double deadlinessDelta) {
		this.deadlinessDelta = deadlinessDelta;
	}

	public void setInfectiousnessDelta(double infectiousnessDelta) {
		this.infectiousnessDelta = infectiousnessDelta;
	}

	public void setNotorietyDelta(double notorietyDelta) {
		this.notorietyDelta = notorietyDelta;
	}

}
