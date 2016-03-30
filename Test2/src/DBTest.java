import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;

public class DBTest {

	public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException{
		try {
			Connector connect = new Connector();
			String userInfo[] = {"myName","12123@123123.dk","123qweasdzxc"};
		//	connect.doUpdate("INSERT INTO `users`(`userName`, `email`, `password`) VALUES ('kaj','svend','victor')");
			connect.doUpdate("INSERT INTO `users`(`userName`, `email`, `password`) VALUES (" + "'" + userInfo[0] + "','" + userInfo[1] +"','" + userInfo[2]+"')");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
