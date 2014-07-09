package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;
import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.gui.MCCGuiScreenMessagePremium;

public class MCCPacketPremiumNeeded extends MCCPacket {

	public String[] message;
	
	@Override
	public void read(DataInputStream in) throws Exception {
		message = new String[in.readInt()];
		for (int i = 0; i != message.length; i++)
			message[i] = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
	}

	@Override
	public void handle() {
		MCC.MCC.showGui(new MCCGuiScreenMessagePremium(MCCGuiScreen.active(), message, true));
	}

}
