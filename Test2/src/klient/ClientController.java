package klient;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.ArrayList;
import ClientConnection.InClientConnection;
import ClientConnection.OutConnectionClient;

import shared.AccountDTO;
import shared.GamePacket;
import shared.Token;

public class ClientController implements Runnable
{

	private Token token;
	private AccountDTO acc;
	private InClientConnection in;
	private OutConnectionClient out;
	private ArrayList<GamePacket> packets = new ArrayList<>();
	DatagramSocket socket = new DatagramSocket();

	public ClientController() throws IOException{
		in = new InClientConnection(socket);
		out = new OutConnectionClient(socket);
	}

	public void sendPackets(GamePacket gp){
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					out.sendPacket(gp);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

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
			System.out.println("Den indkommende pakke er NULL");
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

	public GamePacket initServerConnection() {
		GamePacket initPacket = in.recieve();
		return initPacket;
	}

	public void setAccountDTO(AccountDTO acc){
		this.acc = acc;
	}
	public AccountDTO getAccountDTO(){
		return acc;
	}

	public GamePacket getTables() throws IOException {
		GamePacket tablePacket = in.recieve();
		return tablePacket;


	}
}
