package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import shared.AccountDTO;

public class UserDAO {

	static AccountDTO account = null;
	public static AccountDTO login(String userName, String password) throws Exception{
		Connector connect = new Connector();

		try {
			ResultSet rs = connect.doQuery("SELECT * FROM users WHERE userName = '" +userName +"' AND password = '" +password +"'");
			if(rs.first()){
				account = new AccountDTO(rs.getInt("userID"), rs.getString("userName"), rs.getString("email"), rs.getString("password"));
			}	
		}
		catch (SQLException e) { e.printStackTrace();}

		return account;
	}
		public static boolean createAccount(AccountDTO acc) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			Connector connect = new Connector();
			boolean isCreated = true;
			try {
				connect.doUpdate("INSERT INTO `users`(`userName`, `email`, `password`) VALUES (" + "'" + acc.getUserName() + "','" + acc.getEmail() +"','" + acc.getPassword()+"')");
			
			} catch (SQLException e) {
				
				e.printStackTrace();
				isCreated = false;
			}
			return isCreated;
		}




}
