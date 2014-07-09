package co.minecc.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import co.minecc.client.language.MCCLanguageText;

public class MCCFriend {

	private static final Map<String, MCCFriend> FRIENDS = new HashMap<String, MCCFriend>();
	private static MCCFriend[] sorted = new MCCFriend[]{};
	
	public static MCCFriend get(String id){
		synchronized (FRIENDS) {
			if (FRIENDS.containsKey(id))
				return FRIENDS.get(id);
		}
		
		return new MCCFriend(id);
	}
	
	public static MCCFriend[] getFriends(){
		if (sorted.length == 0)
			order();
		
		return sorted;
	}
	
	public static MCCFriend[] getSearch(String q) {
		final ArrayList<MCCFriend> RESULTS = new ArrayList<MCCFriend>();
		for (final MCCFriend F : getFriends())
			if (F.getName().toLowerCase().contains(q.toLowerCase()))
				RESULTS.add(F);
		return RESULTS.toArray(new MCCFriend[]{});
	}
	
	public static void order() {
		final ArrayList<String> SORT = new ArrayList<String>();
		synchronized (FRIENDS) {
			for (final MCCFriend FRIEND : FRIENDS.values().toArray(new MCCFriend[]{})){
				if (!FRIEND.deleted){
					SORT.add(FRIEND.name + ":" + FRIEND.ID);
				}
			}
		}
		final String[] ORDER = SORT.toArray(new String[]{});
		Arrays.sort(ORDER);
		final MCCFriend[] ORDER_FRIENDS = new MCCFriend[ORDER.length];
		for (int i = 0; i != ORDER.length; i++)
			ORDER_FRIENDS[i] = get(ORDER[i].split(":")[1]);
		sorted = ORDER_FRIENDS;
	}
	
	private boolean deleted = false;
	
	public final String ID;
	private String name = "???";
	private String title = "?";
	private String location = "N/A";
	private boolean online = false;
	private String onlineIP = "";
	private int mccVersion = 0;
	
	public MCCFriend(String i){
		ID = i;
		synchronized (FRIENDS) {
			FRIENDS.put(ID, this);
		}
		order();
	}
	
	@Override
	public String toString() {
		if (title.length() >= 3)
			return name + " " + title;
		else
			return name;
	}
	
	public String toStringServer() {
		if (!online)
			return MCCLanguageText.STATUS_OFFLINE.toString();
		else
			if (onlineIP.length() >= 3)
				return MCCLanguageText.STATUS_ONLINE.toString() + "  " + MCCColour.GREY + onlineIP;
			else
				return MCCLanguageText.STATUS_ONLINE.toString();
	}
	
	public String getName(){
		return name;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getLocation(){
		return location;
	}
	
	public boolean getOnline(){
		return online;
	}
	
	public String getOnlineIP(){
		return onlineIP;
	}
	
	public String getStream(){
		return MCCStream.CHAT_USER + ID;
	}
	
	public MCCChat getChat(){
		return MCCChat.getChat(getStream());
	}
	
	public int getMCCVersion(){
		return mccVersion;
	}
	
	public void setName(String n){
		name = n;
		getChat().setTitle(n);
	}
	
	public void setTitle(String t){
		title = t;
	}
	
	public void setLocation(String l){
		location = l;
	}
	
	public void setOnline(boolean o){
		online = o;
	}
	
	public void setOnlineIP(String s){
		onlineIP = s;
	}
	
	public void setVersion(int version) {
		mccVersion = version;
	}

	public void remove() {
		deleted = true;
		order();
	}
	
	public void removeFully() {
		FRIENDS.remove(ID);
		remove();
	}
	
}
