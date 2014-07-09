package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.gui.MCCGuiScreenFriendsAdd;

public class MCCPacketFriendAdd extends MCCPacket {

	public String data;
	public byte type;
	
	public MCCPacketFriendAdd() {}
	
	public MCCPacketFriendAdd(String d, byte t) {
		data = d;
		type = t;
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
		data = in.readUTF();
		type = in.readByte();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeUTF(data);
		out.writeByte(type);
	}

	@Override
	public void handle() {
		if (type == 0)
			MCCGuiScreenFriendsAdd.setStatus(data, true);
	}

}
