package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;

public class MCCPacketStatus extends MCCPacket {

	public boolean online;
	
	public MCCPacketStatus() {}
	
	public MCCPacketStatus(boolean o) {
		online = o;
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
		online = in.readBoolean();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeBoolean(online);
	}

	@Override
	public void handle() {
		MCC.DATA.online = online;
	}

}
