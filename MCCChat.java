package co.minecc.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.minecc.client.packets.MCCPacketChat;

public class MCCChat {

	private static final Map<String, MCCChat> DATA = new HashMap<String, MCCChat>();
	private static final ArrayList<MCCChat> DATA_ORDERED = new ArrayList<MCCChat>();
	
	public static MCCChat getChat(String s) {
		if (!DATA.containsKey(s)){
			DATA.put(s, new MCCChat(s));
		}
		return DATA.get(s);
	}
	
	public static MCCChat[] getChats() {
		return DATA_ORDERED.toArray(new MCCChat[]{});
	}
	
	public static MCCChat getChatsIndex(int i) {
		try{
			return getChats()[i];
		}catch (Exception e){
			return null;
		}
	}
	
	public final String STREAM;
	private final ArrayList<String> MESSAGES;
	
	private String title = null;
	private boolean flash = false;
	private String input = null;
	private boolean showing = false;
	private boolean typing = false;
	
	public MCCChat(String s) {
		STREAM = s;
		MESSAGES = new ArrayList<String>();
		title = STREAM;
	}
	
	public MCCChat addMessage(String m) {
		if (m == null)
			return this;
		
		if (m.length() == 0)
			return this;
		
		if (!MCC.MCC.getIgnore(STREAM) && !showing) {
			MCC.OVERLAY.notification("Chat - "+ getTitle(), m);
		}
		
		bump();
		MESSAGES.add(new SimpleDateFormat("HH:mm> ").format(new Date()) + '\u2022' + m);
		if (MESSAGES.size() > 64)
			MESSAGES.remove(0);
		return this;
	}
	
	public String getMessage(int i) {
		try{
			return MESSAGES.get(i);
		}catch (Exception e){
			return null;
		}
	}
	
	public String[] getMessages() {
		return MESSAGES.toArray(new String[]{});
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean getFlash() {
		return flash;
	}
	
	public String getInput() {
		if (input == null) {
			return "";
		}
		return input;
	}
	
	public boolean getTyping() {
		return typing;
	}
	
	public void setTitle(String t) {
		title = t;
	}
	
	public void setFlash(boolean f) {
		flash = f;
	}
	
	public void setInput(String i) {
		input = i;
	}
	
	public void setTyping(boolean t) {
		typing = t;
	}
	
	public void bump() {
		DATA_ORDERED.remove(this);
		DATA_ORDERED.add(0, this);
		flash = !showing;
	}
	
	public void send(String input) {
		if (!MCC.MCC.isLoggedIn()) {
			addMessage(MCCColour.RED + "Couldn't send message, no connection to MCC!");
		}else if (input.length() > 0) {
			if (input.length() > 250)
				input = input.substring(0, 250);
			MCC.MCC.send(new MCCPacketChat(STREAM, input));
		}
	}
	
	public void setShowing(boolean s) {
		showing = s;
	}
	
}
