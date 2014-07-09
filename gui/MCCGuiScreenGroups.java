package co.minecc.client.gui;

import org.lwjgl.input.Keyboard;

import co.minecc.client.MCC;
import co.minecc.client.MCCGroup;
import co.minecc.client.MCCGroupInvite;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiButtonArrow;
import co.minecc.client.gui.elements.MCCGuiChat;
import co.minecc.client.gui.elements.MCCGuiElement;

public class MCCGuiScreenGroups extends MCCGuiScreen {

	private static MCCGroup selected = null;
	private static int selectedIndex = 0;
	
	public MCCGuiButton create;
	public MCCGuiButton invites;
	
	public MCCGuiChat chat;
	
	public MCCGuiButton title;
	public MCCGuiButton add;
	
	public MCCGuiScreenGroups(MCCGuiScreen p) {
		super(p);
	}

	@Override
	public void init() {
		Keyboard.enableRepeatEvents(true);
		
		ELEMENTS.add(create = new MCCGuiButton(0, 4, 26, 40, "Create"));
		ELEMENTS.add(invites = new MCCGuiButton(1, 4, create.posY + 22, 40, "Invites"));
		
		ELEMENTS.add(new MCCGuiButtonArrow(16, 10, (height / 2) - 12, MCCGuiButtonArrow.Direction.LEFT));
		ELEMENTS.add(new MCCGuiButtonArrow(17, width - 24, (height / 2 )-  12, MCCGuiButtonArrow.Direction.RIGHT));
		
		ELEMENTS.add(chat = new MCCGuiChat(18, 40, height / 2, width - 80, (height / 2) - 10, null));
		chat.selected = true;
		chat.selectedLock = true;
		
		ELEMENTS.add(title = new MCCGuiButton(2, (width / 2) - 85, chat.posY - 40, 80, "Set Title"));
		ELEMENTS.add(add = new MCCGuiButton(3, title.posX + 90, title.posY, 80, "Add Members"));
	}
	
	@Override
	public void close() {
		super.close();
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		if (selected == null) {
			selected = MCCGroup.get(selectedIndex);
		}
		chat.chat = selected == null ? null : selected.getChat();
		create.enabled = MCC.MCC.isLoggedIn();
		invites.enabled = MCCGroupInvite.next() != null;
		invites.flash = invites.enabled;
		super.draw(cursorX, cursorY);
		
		if (selected != null) {
			drawStringCentered(selected.getTitle(), width / 2, 10);
			drawStringCentered(selected.getMembersString(), width / 2, 20);
		}else{
			drawStringCentered("Groups", width / 2, 10);
		}
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		super.clicked(clicked);
		
		if (clicked.id == create.id)
			MCC.MCC.showGui(new MCCGuiScreenGroupsCreate(this));
		else if (clicked.id == title.id)
			chat.setInput("/title ");
		else if (clicked.id == add.id)
			chat.setInput("/invite ");
		else if (clicked.id == invites.id)
			MCC.MCC.showGui(new MCCGuiScreenGroupsInvites(this));
		
		if (clicked.id == 16 || clicked.id == 17) {
			selectedIndex += clicked.id == 16 ? -1 : 1;
			selected = MCCGroup.get(selectedIndex);
		}
	}

}
