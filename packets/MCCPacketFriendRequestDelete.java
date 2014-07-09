package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class MCCPacketFriendRequestDelete extends MCCPacket {

	public String friend;
	
	public MCCPacketFriendRequestDelete(String f) {
		friend = f;
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeUTF(friend);
	}

	@Override
	public void handle() {
	}

}
