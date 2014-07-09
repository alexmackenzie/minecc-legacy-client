package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCCGroupInvite;

public class MCCPacketGroupInvite extends MCCPacket {

	public String invite;
	public String friend;
	public String group;
	
	public boolean accept;
	
	public MCCPacketGroupInvite() {
	}
	
	public MCCPacketGroupInvite(String i, boolean a) {
		invite = i;
		accept = a;
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
		invite = in.readUTF();
		friend = in.readUTF();
		group = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeUTF(invite);
		out.writeBoolean(accept);
	}

	@Override
	public void handle() {
		new MCCGroupInvite(invite, friend, group);
	}

}
