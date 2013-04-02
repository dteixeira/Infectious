package edu.infectious.logic;

import java.io.Serializable;
import java.util.ArrayList;

import edu.infectious.script.country.Country;
import edu.infectious.script.trait.TraitType;

public class MasterPackage implements Serializable {

	/*
	 * Constants
	 */
	private static final long	serialVersionUID	= 1L;

	/*
	 * Instance fields
	 */
	private boolean				air					= false;
	private int					aridLevel			= 0;
	private int					coldLevel			= 0;
	private ArrayList<Country>	countryList			= null;
	private double				deadliness			= 0.0;
	private boolean				gameOver			= false;
	private int					hotLevel			= 0;
	private double				infectiousness		= 0.0;
	private boolean				livestock			= false;
	private int					mediterraneanLevel	= 0;
	private double				notoriety			= 0.0;
	private boolean				plague				= false;
	private int					temperateLevel		= 0;
	private int					tropicalLevel		= 0;
	private boolean				water				= false;
	private TraitType			winner				= TraitType.VIRUS;

	/*
	 * Constructor
	 */
	public MasterPackage() {}

	/*
	 * Instance methods
	 */
	public int getAridLevel() {
		return aridLevel;
	}

	public int getColdLevel() {
		return coldLevel;
	}

	public ArrayList<Country> getCountryList() {
		return countryList;
	}

	public double getDeadliness() {
		return deadliness;
	}

	public int getHotLevel() {
		return hotLevel;
	}

	public double getInfectiousness() {
		return infectiousness;
	}

	public int getMediterraneanLevel() {
		return mediterraneanLevel;
	}

	public double getNotoriety() {
		return notoriety;
	}

	public int getTemperateLevel() {
		return temperateLevel;
	}

	public int getTropicalLevel() {
		return tropicalLevel;
	}

	public TraitType getWinner() {
		return winner;
	}

	public boolean isAir() {
		return air;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public boolean isLivestock() {
		return livestock;
	}

	public boolean isPlague() {
		return plague;
	}

	public boolean isWater() {
		return water;
	}

	public void setAir(boolean air) {
		this.air = air;
	}

	public void setAridLevel(int aridLevel) {
		this.aridLevel = aridLevel;
	}

	public void setColdLevel(int coldLevel) {
		this.coldLevel = coldLevel;
	}

	public void setCountryList(ArrayList<Country> countryList) {
		this.countryList = countryList;
	}

	public void setDeadliness(double deadliness) {
		this.deadliness = deadliness;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setHotLevel(int hotLevel) {
		this.hotLevel = hotLevel;
	}

	public void setInfectiousness(double infectiousness) {
		this.infectiousness = infectiousness;
	}

	public void setLivestock(boolean livestock) {
		this.livestock = livestock;
	}

	public void setMediterraneanLevel(int mediterraneanLevel) {
		this.mediterraneanLevel = mediterraneanLevel;
	}

	public void setNotoriety(double notoriety) {
		this.notoriety = notoriety;
	}

	public void setPlague(boolean plague) {
		this.plague = plague;
	}

	public void setTemperateLevel(int temperateLevel) {
		this.temperateLevel = temperateLevel;
	}

	public void setTropicalLevel(int tropicalLevel) {
		this.tropicalLevel = tropicalLevel;
	}

	public void setWater(boolean water) {
		this.water = water;
	}

	public void setWinner(TraitType winner) {
		this.winner = winner;
	}

}
