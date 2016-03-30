package ClientConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


import shared.GamePacket;

public class OutConnectionClient{
	private final static int SERVER_in = 9400;
	static byte buffer[];
	static DatagramSocket out;
	static DatagramPacket outPacket;
	static InetAddress inet;
	GamePacket gp;
	static final int SERVER_OUT = 4002;
	
	
	
	private static byte[] packObject(GamePacket gp) throws IOException{
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		System.out.println("Vil gerne sende");
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(gp);
		oo.close();
		buffer = bStream.toByteArray();
		return buffer;
	}
	
	public void sendPacket(GamePacket gp) throws IOException{
		try {
			out = new DatagramSocket(SERVER_OUT);
			inet = InetAddress.getByName("127.0.0.1");
			buffer = packObject(gp);
			outPacket = new DatagramPacket(buffer, buffer.length, inet, SERVER_in );
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						out.send(outPacket);
						System.out.println("sender jeg?");
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
		
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
	}
	
}
