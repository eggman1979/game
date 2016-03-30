package klient;

import shared.GamePacket;

public class GamePacketFactory {

	public GamePacket makeGamePacket(String type, Object object, Object message) {
	
		return new GamePacket(type, null , message);
	}

}
