package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;


public class MCCPacketDisconnect extends MCCPacket {

	public String reason;
	
	@Override
	public void read(DataInputStream in) throws Exception {
		reason = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
	}

	@Override
	public void handle() {
		MCC.MCC.disconnect(reason);
	}

}
