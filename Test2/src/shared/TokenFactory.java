package shared;

import java.util.Date;

public class TokenFactory {

	public Token makeToken(AccountDTO user){
		String tokenID = new Date().toString() + user.getUserName()+user.getUserID();
		return new Token(tokenID);
		
	}
}
