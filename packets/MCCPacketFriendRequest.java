package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;

public class MCCPacketFriendRequest extends MCCPacket {

	public String id;
	public String name;
	
	@Override
	public void read(DataInputStream in) throws Exception {
		id = in.readUTF();
		name = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
	}

	@Override
	public void handle() {
		final String REQUEST = id + ":" + name;
		synchronized (MCC.DATA.requests) {
			if (!MCC.DATA.requests.contains(REQUEST)) {
				MCC.DATA.requests.add(REQUEST);
			}
		}
	}

}
