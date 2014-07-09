package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCCFriend;

public class MCCPacketFriend extends MCCPacket {

	public String id;
	public String name, title;
	public boolean online;
	public String onlineIP;
	public String location;
	public int version;
	
	@Override
	public void read(DataInputStream in) throws Exception {
		id = in.readUTF();
		name = in.readUTF();
		title = in.readUTF();
		online = in.readBoolean();
		onlineIP = in.readUTF();
		location = in.readUTF();
		version = in.readInt();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
	}

	@Override
	public void handle() {
		final MCCFriend FRIEND = new MCCFriend(id);
		FRIEND.setName(name);
		FRIEND.setTitle(title);
		FRIEND.setOnline(online);
		FRIEND.setOnlineIP(onlineIP);
		FRIEND.setLocation(location);
		FRIEND.setVersion(version);
	}

}
