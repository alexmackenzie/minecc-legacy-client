package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;

public class MCCPacketPremium extends MCCPacket {

	public int premium;
	
	public MCCPacketPremium() {
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
		premium = in.readInt();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
	}

	@Override
	public void handle() {
		MCC.DATA.premium = premium >= 0;
		MCC.DATA.premiumExpiry = premium;
	}

}
