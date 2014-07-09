package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCC;
import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.gui.MCCGuiScreenMessageBanned;

public class MCCPacketBanned extends MCCPacket {

	public String date = null, reason = null, admin = null;
	public long unban = -1L;
	
	@Override
	public void read(DataInputStream in) throws Exception {
		date = in.readUTF();
		reason = in.readUTF();
		admin = in.readUTF();
		unban = in.readInt();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {}

	@Override
	public void handle() {
		MCC.MCC.disconnect("You are banned!");
		MCC.MCC.showGui(new MCCGuiScreenMessageBanned(MCCGuiScreen.active(),
				date, reason, admin, getString(unban)));
	}
	
	static final int SECOND = 1, MINUTE = SECOND * 60, HOUR = MINUTE * 60;
	
	private static String getString(long u){
		if (0 > u){return null;}
		final int HOURS = (int) (u / HOUR);
		u -= HOURS * HOUR;
		final int MINUTES = (int) (u / MINUTE);
		u -= MINUTES * MINUTE;
		final int SECONDS = (int) (u / SECOND);
		String s = "";
		if (HOURS > 0){
			s = HOURS + "H ";
		}if (MINUTES > 0){
			s = s + MINUTES + "M ";
		}if (SECONDS > 0){
			s = s + SECONDS + "S ";
		}
		return s;
	}

}
