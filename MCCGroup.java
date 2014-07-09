package co.minecc.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MCCGroup implements Comparable<MCCGroup> {

	public static final String LEAVE = "/leave";
	
	private static final Map<String, MCCGroup> GROUPS = new HashMap<String, MCCGroup>();
	private static final ArrayList<MCCGroup> GROUPS_ORDERED = new ArrayList<MCCGroup>();
	
	public static MCCGroup get(String i) {
		synchronized (GROUPS) {
			if (GROUPS.containsKey(i))
				return GROUPS.get(i);
		}
		return new MCCGroup(i);
	}
	
	public static MCCGroup get(int i) {
		final MCCGroup[] ALL = getAll();
		if (ALL.length == 0)
			return null;
		
		return ALL[Math.abs(i) % ALL.length];
	}
	
	public static int getIndex(MCCGroup g) {
		final MCCGroup[] ALL = getAll();
		for (int i = 0; i != ALL.length; i++) {
			if (ALL[i].ID.equals(g.ID))
				return i;
		}
		return ALL.length;
	}
	
	public static MCCGroup[] getAll() {
		synchronized (GROUPS) {
			return GROUPS.values().toArray(new MCCGroup[] {});
		}
	}
	
	public final String ID;
	
	private String title = "Group Title";
	private boolean member = false;
	private String[] members = new String[] {};
	
	private MCCGroup(String i) {
		ID = i;
		synchronized (GROUPS) {
			GROUPS.put(ID, this);
			synchronized (GROUPS_ORDERED) {
				GROUPS_ORDERED.clear();
				GROUPS_ORDERED.addAll(GROUPS.values());
				Collections.sort(GROUPS_ORDERED);
			}
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public String[] getMembers() {
		return members;
	}
	
	public String getMembersString() {
		return members.length + " member" + (members.length != 1 ? "s" : "");
	}
	
	public MCCChat getChat() {
		return MCCChat.getChat(MCCStream.CHAT_GROUP + ID);
	}
	
	public boolean isMember() {
		return member;
	}
	
	public void setTitle(String t) {
		title = t;
		getChat().setTitle(MCCColour.GREEN + t);
	}
	
	public void setMembers(String[] m) {
		members = m;
	}
	
	public void setMember(boolean m) {
		member = m;
	}

	@Override
	public int compareTo(MCCGroup g) {
		return g.getTitle().compareTo(getTitle());
	}
	
}
