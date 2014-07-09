package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;

public class MCCPacketTitle extends MCCPacket {

	public String title;
	
	@Override
	public void read(DataInputStream in) throws Exception {
		title = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
	}

	@Override
	public void handle() {
		MCC.DATA.title = title.length() > 0 ? title : null;
	}

}
