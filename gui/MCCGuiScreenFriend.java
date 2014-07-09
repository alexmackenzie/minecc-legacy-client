package co.minecc.client.gui;

import org.lwjgl.input.Keyboard;

import co.minecc.client.MCC;
import co.minecc.client.MCCColour;
import co.minecc.client.MCCFriend;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiChat;
import co.minecc.client.gui.elements.MCCGuiElement;
import co.minecc.client.language.MCCLanguageText;
import co.minecc.client.packets.MCCPacketFriendDelete;

public class MCCGuiScreenFriend extends MCCGuiScreen {

	public final String ID;
	
	public MCCGuiButton buttonJoin;
	public MCCGuiButton buttonChat;
	public MCCGuiButton buttonDelete;
	
	public MCCGuiScreenFriend(MCCGuiScreen p, MCCFriend f) {
		super(p);
		ID = f.ID;
	}

	@Override
	public void init() {
		Keyboard.enableRepeatEvents(true);
		int chatWidth = width / 3;
		int chatHeight = (int) ((double) height / 1.5);
		if (chatWidth > 50 && chatHeight > 50)
			ELEMENTS.add(new MCCGuiChat(64, width - chatWidth, height - (chatHeight + 5), chatWidth, chatHeight, MCCFriend.get(ID).getChat()));
		ELEMENTS.add(buttonJoin = new MCCGuiButton(32, 4, height - 32, 100, MCCLanguageText.FRIENDS_PROFILE_JOIN));
		ELEMENTS.add(buttonChat = new MCCGuiButton(33, 105, buttonJoin.posY, 80, MCCLanguageText.FRIENDS_PROFILE_CHAT));
		ELEMENTS.add(buttonDelete = new MCCGuiButton(34, 186, buttonJoin.posY, 80, MCCColour.RED + MCCLanguageText.FRIENDS_PROFILE_REMOVE.toString()));
	}
	
	@Override
	public void close() {
		Keyboard.enableRepeatEvents(false);
		super.close();
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		final MCCFriend FRIEND = MCCFriend.get(ID);
		drawStringCentered(FRIEND.getName(), width / 2, 10);
		if (FRIEND.getTitle().length() >= 3){
			drawStringCentered(FRIEND.getTitle(), width / 2, 20);
		}
		buttonJoin.enabled = FRIEND.getOnlineIP().length() >= 1 && FRIEND.getOnline();
		buttonJoin.flash = buttonJoin.enabled;
		/* Name */
		drawString(FRIEND.getName(), 4, 40);
		/* Status */
		drawString(FRIEND.getOnline() ? MCCColour.GREEN + "Online" : MCCColour.RED + "Offline", 4, 60);
		if (FRIEND.getOnline())
			drawString(FRIEND.getOnlineIP(), 4, 70, 0xDDDDDD);
		/* General Info */
		drawString(FRIEND.getLocation(), 4, 90);
		/* ID */
		drawString("ID: " + FRIEND.ID, 4, height - 11, 0xBBBBBB);
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		super.clicked(clicked);
		final MCCFriend FRIEND = MCCFriend.get(ID);
		if (clicked.id == buttonJoin.id)
			MCC.MCC.join(FRIEND.getOnlineIP());
		else if (clicked.id == buttonChat.id)
			MCC.MCC.showGui(new MCCGuiScreenChat(this, FRIEND.getChat()));
		else if (clicked.id == buttonDelete.id)
			MCC.MCC.showGui(new MCCGuiScreenChoice(this, new String[]{
					"Do you really want to remove " + FRIEND.getName() + "?"
			}, new String[]{MCCLanguageText.YES.toString(), MCCLanguageText.NO.toString()},
			new int[]{16, 17}));
	}
	
	@Override
	public void choice(int id) {
		final MCCFriend FRIEND = MCCFriend.get(ID);
		if (id == 16) {
			MCC.MCC.showGui(PARENT);
			MCC.MCC.send(new MCCPacketFriendDelete(FRIEND.ID));
			FRIEND.remove();
		}
	}

}
