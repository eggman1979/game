package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import shared.GamePacket;

public class InConnectionServer implements Runnable{



	private DatagramSocket inSocket;
	private DatagramPacket inPacket;
	private byte buffer[];
	ArrayList<GamePacket> loginPacketQueue = new ArrayList<>();







	public InConnectionServer(int port) throws SocketException  {


		inSocket = new DatagramSocket(port);
	}



	@Override
	public synchronized void run() {
		while(true){
			try {
				buffer = new byte[1048];
				inPacket = new DatagramPacket(buffer, buffer.length);
				inSocket.receive(inPacket);

				try {
					processInputPacket(inPacket.getData());
				} catch (ClassNotFoundException | InterruptedException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {


			}
		}

	}

	private void processInputPacket(byte[] data) throws IOException, ClassNotFoundException, InterruptedException{

		ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));
		GamePacket packet = (GamePacket) iStream.readObject();
		if(packet.getHeader().equals("login")||packet.getHeader().equals("create")){
			synchronized (this) {
				loginPacketQueue.add(packet);		
			}
		}
		iStream.close();
	}
	public ArrayList<GamePacket> getLoginPacketQueue(){
		return loginPacketQueue;
	}
	
}
