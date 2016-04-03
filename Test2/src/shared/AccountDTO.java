package shared;

import java.io.Serializable;
import java.net.InetAddress;

public class AccountDTO implements Serializable{

	private static final long serialVersionUID = -8821427769954845335L;
	
	int userID;
	String userName;
	String email;
	String password;
	InetAddress clientAddress;
	Token token;
	

	/**
	 * Konstruktør til klienten kan sende password og userName til serveren
	 * @param userName - Brugerens navn
	 * @param password - brugerens password
	 */
	public AccountDTO(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	// Konstruktør til oprettelse af accountobjekter
	public AccountDTO(String userName, String email, String password){
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	
	// Fuld account konstruktør
	public AccountDTO(int userID, String userName, String email, String password){
		this.userID = userID;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserID() {
		return userID;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setClientAddress(InetAddress clientAddress){
		this.clientAddress = clientAddress;
	}
	public InetAddress getClientAddress(){
		return clientAddress;
	}
	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	public String toString(){
		return "userID " + userID + " username " + userName + " email: " + email;
	}
}
