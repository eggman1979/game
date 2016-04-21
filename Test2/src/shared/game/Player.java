package shared.game;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = -6032509606613961674L;
	int score;
	int userID;
	String userName;
	public Player(int userID, int score, String userName){
		this.score = score;
		this.userID = userID;
		this.userName = userName;
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
