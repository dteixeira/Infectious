package edu.infectious.logic;

import java.io.Serializable;

public class SlavePackage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private double deadlinessDelta = 0.0;
	private double infectiousnessDelta = 0.0;
	private double notorietyDelta = 0.0;
	
	public SlavePackage() {
	}

	public double getDeadlinessDelta() {
		return deadlinessDelta;
	}

	public void setDeadlinessDelta(double deadlinessDelta) {
		this.deadlinessDelta = deadlinessDelta;
	}

	public double getInfectiousnessDelta() {
		return infectiousnessDelta;
	}

	public void setInfectiousnessDelta(double infectiousnessDelta) {
		this.infectiousnessDelta = infectiousnessDelta;
	}

	public double getNotorietyDelta() {
		return notorietyDelta;
	}

	public void setNotorietyDelta(double notorietyDelta) {
		this.notorietyDelta = notorietyDelta;
	}

}
