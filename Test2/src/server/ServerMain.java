package server;

import java.io.IOException;

public class ServerMain {

	public static void main(String[] args) throws IOException{
		System.out.println("Starter");
		(new Thread(new LoginServer())).start();
	
	}
}
