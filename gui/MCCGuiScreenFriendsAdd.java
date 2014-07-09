package co.minecc.client.gui;

import org.lwjgl.input.Keyboard;

import co.minecc.client.MCC;
import co.minecc.client.gui.elements.MCCGuiText;
import co.minecc.client.gui.elements.MCCGuiText.MCCGuiTextHandler;
import co.minecc.client.packets.MCCPacketFriendAdd;

public class MCCGuiScreenFriendsAdd extends MCCGuiScreen {

	private static String status = "";
	private static boolean statusResult = true;
	
	public static String getStatus() {
		return status;
	}
	
	public static boolean getStatusResult() {
		return statusResult;
	}
	
	public static void setStatus(String t, boolean r) {
		status = t;
		statusResult = r;
	}
	
	public MCCGuiText text;
	
	public MCCGuiScreenFriendsAdd(MCCGuiScreen p) {
		super(p);
	}

	@Override
	public void init() {
		Keyboard.enableRepeatEvents(true);
		ELEMENTS.add(text = new MCCGuiText(0, 4, 40, 200, new MCCGuiTextHandler() {
			
			@Override
			public void handle(MCCGuiText t, String i) {
				if (i.length() == 0)
					return;
				
				if (MCC.MCC.send(new MCCPacketFriendAdd(i, (byte) 0x0))) {
					setStatus("Sending request...", false);
				}else{
					setStatus("No connection to MCC!", true);
				}
			}
		}));
		text.selectedLock = true;
		text.valid = "abcdefghijklmnopqrstuvwxyz_0123456789";
		text.max = 16;
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		drawStringCentered("Add Friend", width / 2, 10);
		text.selected = statusResult;
		drawString("Username:", 4, 30);
		if (text.selected) {
			drawString("Press ENTER to send request.", 4, 54);
		}
		if (status.length() >= 1)
			drawString("Result: " + status, 4, 70);
		
		if (text.getInput().length() >= 2) {
			drawString(text.getInput() + " will be asked to confirm this request.", 4, 100);
		}
	}
	
	@Override
	public void close() {
		Keyboard.enableRepeatEvents(false);
		super.close();
	}
}
