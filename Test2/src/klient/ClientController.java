package klient;

import java.io.IOException;
import java.util.ArrayList;



import ClientConnection.InClientConnection;
import ClientConnection.OutConnectionClient;

import shared.GamePacket;
import shared.Token;

public class ClientController implements Runnable
{

	private Token token;
	private ClientStates states;
	private InClientConnection in;
	private OutConnectionClient out;
	private GamePacket gp;
	private ArrayList<Runnable> observers = new ArrayList<>();
	private ArrayList<GamePacket> packets = new ArrayList<>();



	public ClientController() throws IOException{

		in = new InClientConnection();
		out = new OutConnectionClient();

		//new Thread(this).start();

	}

	public void sendPackets(GamePacket gp){
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					out.sendPacket(gp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

	}
	public void setState(ClientStates states){
		this.states = states;
	}
	public ClientStates getState(){
		return states;
	}


	// Får besked fra In at der er en pakke der skal håndteres
	@Override
	public void run() {
		while(true){
			GamePacket inc = in.recieve();
			handlePackets(inc);

		}
	}


	// Beslutter hvad der skal ske med de indkommende pakker
	private void handlePackets(GamePacket inc) {

		packets.add(inc);

	}

	public Token getToken(){
		return token;
	}

	public GamePacket getLoginPacket(){

		GamePacket loginPacket = null;
		try{

			loginPacket = in.recieve();

		}catch(NullPointerException e){
			e.printStackTrace();
		}
		return loginPacket;





	}

	public GamePacket getCreateAccountPacket() {
		GamePacket createPacket = null;
		try{

			createPacket = in.recieve();

		}catch(NullPointerException e){
			e.printStackTrace();
		}
		return createPacket;
	}


}
