package edu.infectious.logic;

public class Player {

	private static final int TURN_POINTS_INC = 5;
	private static final int MAX_POINTS = 30;
	
	private int playerPoints;
	private PlayerType playerType;
	
	public Player(PlayerType playerType) {
		playerPoints = 0;
		this.playerType = playerType;
	}
	
	public boolean isAffordable(int cost) {
		return playerPoints >= cost;
	}
	
	public void spendPoints(int cost) {
		playerPoints -= cost;
	}
	
	public void resetPoints() {
		playerPoints = 0;
	}
	
	public void nextTurnPoints() {
		playerPoints += TURN_POINTS_INC;
		if(playerPoints > MAX_POINTS)
			playerPoints = MAX_POINTS;
	}

	public int getPlayerPoints() {
		return playerPoints;
	}

	public PlayerType getPlayerType() {
		return playerType;
	}
	
	
}
