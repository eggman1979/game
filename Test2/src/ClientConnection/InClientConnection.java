package ClientConnection;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


import shared.GamePacket;

public class InClientConnection{


	DatagramSocket in;
	DatagramPacket inPacket;

	InetAddress iNet;
	byte buffer[];


	public InClientConnection(DatagramSocket socket) throws IOException{
		this.in = socket;
	}


	public GamePacket recieve(){	
		buffer = new byte[2048];
		inPacket = new DatagramPacket(buffer, buffer.length);
		GamePacket recievedPacket = null;

		try {
			in.setSoTimeout(1000 * 21);
			System.out.println(in.getSoTimeout());
			in.receive(inPacket);
			System.out.println("Der er blevet modtaget noget " + inPacket);
			recievedPacket = processInputPacket(inPacket.getData());

		} catch (ClassNotFoundException  | InterruptedException e) {
			e.printStackTrace();
		}
		catch(IOException e){

		}
		return recievedPacket;
	}


	private GamePacket processInputPacket(byte[] data) throws IOException, ClassNotFoundException, InterruptedException{
		System.out.println("kommer jeg i processInput?");
		ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));
		GamePacket packet = (GamePacket) iStream.readObject();
		System.out.println("Pakkan er: " + packet.getHeader() + " "+ packet.getToken()+ " " + packet.getPayload());

		iStream.close();
		return packet;
	}


}
