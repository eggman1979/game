package server;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import shared.GamePacket;

public class OutConnectionServer {
	DatagramSocket  out;
	DatagramPacket outPacket;
	InetAddress iNet;
	byte buffer[];
	public OutConnectionServer() throws IOException{
		out = new DatagramSocket(9401);
		iNet = InetAddress.getByName("127.0.0.1");
	}

	public byte[] packObject(GamePacket gp) throws IOException{
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		System.out.println("Serveren : Vil gerne sende");
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(gp);
		oo.close();

		this.buffer = bStream.toByteArray();
		return buffer;

	}

	public void sendMessage(GamePacket gp) throws IOException{

		buffer = packObject(gp);
		outPacket = new DatagramPacket(buffer, buffer.length, iNet, 9999);
		out.send(outPacket);
	}

}
