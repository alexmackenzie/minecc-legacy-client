package co.minecc.client.gui;

import co.minecc.client.MCC;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;
import co.minecc.client.language.MCCLanguageText;
import co.minecc.client.packets.MCCPacketFriendAdd;
import co.minecc.client.packets.MCCPacketFriendRequestDelete;

public class MCCGuiScreenFriendsRequests extends MCCGuiScreen {

	public MCCGuiButton buttonYes;
	public MCCGuiButton buttonNo;
	
	public String id = null, name = null;
	
	public MCCGuiScreenFriendsRequests(MCCGuiScreen p) {
		super(p);
	}

	@Override
	public void init() {
		ELEMENTS.add(buttonYes = new MCCGuiButton(0, (width / 2) - 60, (height / 2) - 10, 120,
				MCCLanguageText.FRIENDS_REQUESTS_OK));
		ELEMENTS.add(buttonNo = new MCCGuiButton(1, (width / 2) - 60, (height / 2) + 15, 120,
				MCCLanguageText.FRIENDS_REQUESTS_DENY));
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		if (id == null || name == null)
			update();
		drawStringCentered(MCCLanguageText.FRIENDS_REQUESTS.toString(), width / 2, 10);
		boolean enabled = id != null;
		buttonYes.enabled = enabled;
		buttonNo.enabled = enabled;
		if (enabled) {
			drawStringCentered(name + " " + MCCLanguageText.FRIENDS_REQUESTS_REQUEST, width / 2, (height / 2) - 40);
		}else{
			drawStringCentered(MCCLanguageText.FRIENDS_REQUESTS_NONE.toString(), width / 2, (height / 2) - 40);
		}
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		super.clicked(clicked);
		synchronized (MCC.DATA.requests) {
			if (clicked.id == buttonYes.id) {
				MCC.MCC.send(new MCCPacketFriendAdd(name, (byte) 0x1));
				MCC.DATA.requests.remove(0);
			}else if (clicked.id == buttonNo.id) {
				MCC.MCC.send(new MCCPacketFriendRequestDelete(id));
				MCC.DATA.requests.remove(0);
			}
		}
		update();
	}
	
	public void update() {
		synchronized (MCC.DATA.requests) {
			try {
				final String[] REQUEST = MCC.DATA.requests.get(0).split(":");
				id = REQUEST[0];
				name = REQUEST[1];
			}catch (Exception e){
				id = null;
				name = null;
			}
		}
	}

}
