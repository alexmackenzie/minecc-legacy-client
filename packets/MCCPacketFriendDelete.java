package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCCFriend;

public class MCCPacketFriendDelete extends MCCPacket {

	public String friend;
	
	public MCCPacketFriendDelete() {};
	
	public MCCPacketFriendDelete(String f) {
		friend = f;
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
		friend = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeUTF(friend);
	}

	@Override
	public void handle() {
		final MCCFriend FRIEND = MCCFriend.get(friend);
		
		if (FRIEND != null)
			FRIEND.removeFully();
	}

}
