package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;
import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.gui.MCCGuiScreenMessageWelcome;

public class MCCPacketNew extends MCCPacket {

	@Override
	public void read(DataInputStream in) throws Exception {}

	@Override
	public void write(DataOutputStream out) throws Exception {}

	@Override
	public void handle() {
		MCC.MCC.showGui(new MCCGuiScreenMessageWelcome(MCCGuiScreen.active()));
	}

}
