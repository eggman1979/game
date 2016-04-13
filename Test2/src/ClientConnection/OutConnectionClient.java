package ClientConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import shared.GamePacket;

public class OutConnectionClient{
	private  int SERVER_in = 9400;
	static byte buffer[];
	static DatagramSocket out;
	static DatagramPacket outPacket;
	 InetAddress INET;
	GamePacket gp;
	static final int SERVER_OUT = 4002;
	
	
	
	public OutConnectionClient(DatagramSocket out) throws UnknownHostException, SocketException{
		INET = InetAddress.getByName("ubuntu4.javabog.dk");
		this.out = out;
		
		out.connect(INET, SERVER_in);
		
	}
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
			
//			inet = InetAddress.getLocalHost();
			buffer = packObject(gp);
			outPacket = new DatagramPacket(buffer, buffer.length, INET, SERVER_in );
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						out.send(outPacket);
						
						System.out.println("sender jeg?");
					
						
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
