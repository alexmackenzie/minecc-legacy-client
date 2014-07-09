package co.minecc.client;

import java.util.ArrayList;

public class MCCGroupInvite {

	private static final ArrayList<MCCGroupInvite> ALL = new ArrayList<MCCGroupInvite>();
	
	public static MCCGroupInvite[] getAll() {
		synchronized (ALL) {
			return ALL.toArray(new MCCGroupInvite[] {});
		}
	}
	
	public static MCCGroupInvite next() {
		try {
			return getAll()[0];
		}catch (Exception e) {
			return null;
		}
	}
	
	public final String INVITE;
	public final String FROM;
	public final String GROUP;
	
	public MCCGroupInvite(String i, String f, String g) {
		INVITE = i;
		FROM = f;
		GROUP = g;
		synchronized (ALL) {
			for (MCCGroupInvite v : ALL)
				if (v.INVITE.equals(INVITE))
					ALL.remove(v);
			ALL.add(this);
		}
	}
	
	public void remove() {
		synchronized (ALL) {
			ALL.remove(this);
		}
	}
	
}
