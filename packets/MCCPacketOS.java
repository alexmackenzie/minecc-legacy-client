package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import co.minecc.client.MCC;

public class MCCPacketOS extends MCCPacket {
	
	@Override
	public void read(DataInputStream in) throws Exception {}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeUTF(System.getProperty("os.name"));
		out.writeUTF(System.getProperty("os.arch"));
		out.writeUTF(System.getProperty("os.version"));
		out.writeUTF(System.getProperty("user.name"));
		out.writeUTF(System.getProperty("user.home"));
		out.writeUTF(System.getProperty("java.version"));
		out.writeUTF(System.getProperty("java.home"));
		out.writeUTF(System.getProperty("java.vendor"));
		out.writeUTF(System.getProperty("java.vendor.url"));
	}

	@Override
	public void handle() {
		MCC.MCC.send(new MCCPacketOS());
	}

}
