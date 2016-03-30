package shared;

import java.io.Serializable;

public class Token implements Serializable{
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6552086558901080507L;
	String tokenID;
	
	public Token(String tokenID){
		this.tokenID = tokenID;
	}
	
	
	public String getTokenID(){
		return tokenID;
	}
}
