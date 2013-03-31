package edu.infectious.logic;

import edu.infectious.script.trait.TraitType;

public class Player {

	/*
	 * Constants
	 */
	private static final int	MAX_POINTS		= 30;
	private static final int	TURN_POINTS_INC	= 5;

	/*
	 * Instance fields
	 */
	private int					playerPoints;
	private TraitType			playerType;

	/*
	 * Constructor
	 */
	public Player(TraitType playerType) {
		playerPoints = 0;
		this.playerType = playerType;
	}

	/*
	 * Instance methods
	 */
	public int getPlayerPoints() {
		return playerPoints;
	}

	public TraitType getPlayerType() {
		return playerType;
	}

	public boolean isAffordable(int cost) {
		return playerPoints >= cost;
	}

	public void nextTurnPoints() {
		playerPoints += TURN_POINTS_INC;
		if (playerPoints > MAX_POINTS)
			playerPoints = MAX_POINTS;
	}

	public void resetPoints() {
		playerPoints = 0;
	}

	public void spendPoints(int cost) {
		playerPoints -= cost;
	}

}
