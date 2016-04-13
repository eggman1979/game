/**
 * 
 */
package shared;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * @author KimDrewes
 * Ideen med denne klasse er at have et format til at kunne sende objekter frem og tilbage
 * Der skal v�re en header, der g�r at de hurtigt kan sorteres imellem.
 * Udover headeren skal der v�re et Objekt der kan pakkes ud og hente data.
 */
public class GamePacket implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4883543073133580362L;

	/**
	 * 
	 */
	
	private Token token;
	private String header; // Headeren der identificerer pakken
	private InetAddress inetAddress; // Hvem der har send pakken.
	private int port;
	private Object payload;
	
	public GamePacket(String header, Token token, Object payload){
		this.header = header;
		
		this.payload = payload;
		this.token = token;
	}

	// Getters
	public String getHeader() {
		return header;
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}
	
	public void setInetAddress(InetAddress inet){
		this.inetAddress = inet;
	}

	public Object getPayload() {
		return payload;
	}

	public Token getToken() {
		return token;
	}
	public void setPort(int port){
		this.port = port;
	}
	public int getPort(){
	 return port;
	}
}