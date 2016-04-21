package shared.game;

import java.io.Serializable;

public class Table implements Serializable {


	private static final long serialVersionUID = -5543435514917106976L;
	private String tableName;
	int gameID;
	Player players[] = new Player[4];
	private String noOfPlayers;
	Chat chat = new Chat();
	boolean isReady[] = new boolean[4];
	
	public Table(String tableName, int noOfPlayers){
		this.tableName = tableName;
		this.noOfPlayers = noOfPlayers+"/4";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getNoOfPlayers() {
		return noOfPlayers;
	}

	public void setNoOfPlayers(String noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public boolean[] getIsReady() {
		return isReady;
	}

	public void setIsReady(boolean[] isReady) {
		this.isReady = isReady;
	}
	
}
