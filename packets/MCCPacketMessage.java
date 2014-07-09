package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;
import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.gui.MCCGuiScreenMessage;

public class MCCPacketMessage extends MCCPacket {

	public String[] content;
	
	@Override
	public void read(DataInputStream in) throws Exception {
		content = new String[in.readInt()];
		for (int i = 0; i != content.length; i++)
			content[i] = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
	}

	@Override
	public void handle() {
		MCC.MCC.showGui(new MCCGuiScreenMessage(MCCGuiScreen.active(), content));
	}

}
