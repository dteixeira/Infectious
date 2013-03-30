package edu.infectious.logic;

import java.io.Serializable;
import java.util.ArrayList;
import edu.infectious.script.country.Country;
import edu.infectious.script.trait.TraitType;

public class MasterPackage implements Serializable {

	private static final long serialVersionUID = 1L;
	private double deadliness = 0.0;
	private double infectiousness = 0.0;
	private double notoriety = 0.0;
	private ArrayList<Country> countryList = null;
	private boolean air = false;
	private boolean water = false;
	private boolean plague = false;
	private boolean livestock = false;
	private int hotLevel = 0;
	private int coldLevel = 0;
	private int temperateLevel = 0;
	private int aridLevel = 0;
	private int tropicalLevel = 0;
	private int mediterraneanLevel = 0;
	private boolean gameOver = false;
	private TraitType winner = TraitType.VIRUS;
	
	public MasterPackage() {
	}

	public double getDeadliness() {
		return deadliness;
	}

	public void setDeadliness(double deadliness) {
		this.deadliness = deadliness;
	}

	public double getInfectiousness() {
		return infectiousness;
	}

	public void setInfectiousness(double infectiousness) {
		this.infectiousness = infectiousness;
	}

	public double getNotoriety() {
		return notoriety;
	}

	public void setNotoriety(double notoriety) {
		this.notoriety = notoriety;
	}

	public ArrayList<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(ArrayList<Country> countryList) {
		this.countryList = countryList;
	}

	public boolean isAir() {
		return air;
	}

	public void setAir(boolean air) {
		this.air = air;
	}

	public boolean isWater() {
		return water;
	}

	public void setWater(boolean water) {
		this.water = water;
	}

	public boolean isPlague() {
		return plague;
	}

	public void setPlague(boolean plague) {
		this.plague = plague;
	}

	public boolean isLivestock() {
		return livestock;
	}

	public void setLivestock(boolean livestock) {
		this.livestock = livestock;
	}

	public int getHotLevel() {
		return hotLevel;
	}

	public void setHotLevel(int hotLevel) {
		this.hotLevel = hotLevel;
	}

	public int getColdLevel() {
		return coldLevel;
	}

	public void setColdLevel(int coldLevel) {
		this.coldLevel = coldLevel;
	}

	public int getTemperateLevel() {
		return temperateLevel;
	}

	public void setTemperateLevel(int temperateLevel) {
		this.temperateLevel = temperateLevel;
	}

	public int getAridLevel() {
		return aridLevel;
	}

	public void setAridLevel(int aridLevel) {
		this.aridLevel = aridLevel;
	}

	public int getTropicalLevel() {
		return tropicalLevel;
	}

	public void setTropicalLevel(int tropicalLevel) {
		this.tropicalLevel = tropicalLevel;
	}

	public int getMediterraneanLevel() {
		return mediterraneanLevel;
	}

	public void setMediterraneanLevel(int mediterraneanLevel) {
		this.mediterraneanLevel = mediterraneanLevel;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public TraitType getWinner() {
		return winner;
	}

	public void setWinner(TraitType winner) {
		this.winner = winner;
	}

}
