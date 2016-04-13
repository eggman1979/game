package game;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = -6032509606613961674L;
	int score;
	int userID;
	public Player(int userID, int score){
		this.score = score;
		this.userID = userID;
	}


	public int getScore() {
		return score;
	}
	/**
	 * Method that adds the score	
	 * @param score - The Points earned in game finished
	 */
	public void setScore(int score) {
		this.score = this.score + score;
	}
	public int getUserID() {
		return userID;
	}
}
