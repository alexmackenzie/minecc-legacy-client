package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;

public class MCCPacketMultiplayer extends MCCPacket {

	public String address;
	
	public MCCPacketMultiplayer() {}
	
	public MCCPacketMultiplayer(String i) {
		if (i == null)
			i = "";
		address = i;
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
		address = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeUTF(address);
	}

	@Override
	public void handle() {
		MCC.MCC.join(address);
	}

}
