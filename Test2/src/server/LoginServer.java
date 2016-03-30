package server;

import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.UserDAO;
import shared.AccountDTO;
import shared.GamePacket;
import shared.Token;
import shared.TokenFactory;

/**
 * @author KimDrewes
 *
 */
public class LoginServer implements Runnable {
	InConnectionServer ics ;
	OutConnectionServer ocs;
	ArrayList<GamePacket> outPackets = new ArrayList<>();
	Token token = null;
	TokenFactory tf = new TokenFactory();
	public LoginServer() throws IOException {
		try {
			ics = new InConnectionServer(9400);
			ocs = new OutConnectionServer();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		new Thread(ics).start();
	}

	@Override
	public void run() {

		while(true){

			synchronized (this) {
				if(ics.getLoginPacketQueue().size() > 0){
					try {
						handleLoginPacket();
					} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
	}			


	public void handleLoginPacket() throws Exception{
		// TODO Mangler en måde at gemme tokens, for at se hvem der logget ind!
		
		//Henter den første packet fra loginKøen, og sletter den bagefter
		GamePacket loginPacket = ics.getLoginPacketQueue().get(0);
		ics.getLoginPacketQueue().remove(0);
		
		Object info =  loginPacket.getPayload();
		
		
		if(loginPacket.getHeader().equals("login")){
			AccountDTO user;
			String loginInfo[] =  ((String) info).split(",");
			System.out.println(loginInfo[0] + " " + loginInfo[1]);
			user = UserDAO.login(loginInfo[0], loginInfo[1]);
			try{
			if(user != null){
				token = tf.makeToken(user);
				System.out.println(token.getTokenID());
				System.out.println(user);
			}
			}catch(NullPointerException e){
				e.printStackTrace();
				System.out.println("bruger findes ikke");
			}finally{
			ocs.sendMessage(new GamePacket("login", token, user));
			}
		}
		else if(loginPacket.getHeader().equals("create")){
			AccountDTO acc = (AccountDTO) info;
			if(UserDAO.createAccount(acc)){
				ocs.sendMessage(new GamePacket("create", token, UserDAO.login(acc.getUserName(), acc.getPassword())));
			}else{
				ocs.sendMessage(new GamePacket("create", token, "error"));
			}
			
		}
		// Sletter Token når den er færdig
		token = null;

	}


}
