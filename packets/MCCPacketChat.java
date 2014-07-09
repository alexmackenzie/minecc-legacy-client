package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCCChat;

public class MCCPacketChat extends MCCPacket {

	public String target;
	public String message;
	
	public MCCPacketChat(){
	}
	
	public MCCPacketChat(String f, String m){
		target = f;
		message = m;
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
		target = in.readUTF();
		message = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeUTF(target);
		out.writeUTF(message);
	}

	@Override
	public void handle() {
		MCCChat.getChat(target).addMessage(message);
	}

}
