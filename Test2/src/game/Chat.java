package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Chat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6969354425982449163L;
	ArrayList<String> chat = new ArrayList<String>();
	
	public ArrayList<String> submitEntry(String entry){
		chat.add(new Date() + ": -> entry");
		return chat;
	}
	
	public ArrayList<String> getChat(){
		return chat;
	}
}
