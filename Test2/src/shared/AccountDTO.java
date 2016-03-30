package shared;

import java.io.Serializable;

public class AccountDTO implements Serializable{

	int userID;
	String userName;
	String email;
	String password;
	
	// Konstruktør til oprettelse af accountobjekter
	public AccountDTO(String userName, String email, String password){
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	
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
	
	public String toString(){
		return "userID " + userID + " username " + userName + " email: " + email;
	}
}
