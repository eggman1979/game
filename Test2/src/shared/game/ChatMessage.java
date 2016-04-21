package shared.game;

import java.io.Serializable;

public class ChatMessage implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4461218063452121039L;
	private String message;
	private int gameID;

	public ChatMessage(String message, int gameID){
		this.message = message;
		this.gameID = gameID;
	}
	
	public String getMessage(){
		return message;
	}
	public int getGameID(){
		return gameID;
	}
}
