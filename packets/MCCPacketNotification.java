package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;


public class MCCPacketNotification extends MCCPacket {

	public String title, message, stream;
	
	@Override
	public void read(DataInputStream in) throws Exception {
		title = in.readUTF();
		message = in.readUTF();
		stream = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
	}

	@Override
	public void handle() {
		if (!MCC.MCC.getIgnore(stream)){
			MCC.OVERLAY.notification(title, message);
		}
	}

}
