package co.minecc.client.gui;

import org.lwjgl.input.Keyboard;

import co.minecc.client.MCC;
import co.minecc.client.MCCColour;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;
import co.minecc.client.gui.elements.MCCGuiText;
import co.minecc.client.packets.MCCPacketGroup;

public class MCCGuiScreenGroupsCreate extends MCCGuiScreen {

	private String status = null;
	
	public MCCGuiText title;
	public MCCGuiText members;
	
	public MCCGuiScreenGroupsCreate(MCCGuiScreen p) {
		super(p);
	}

	@Override
	public void init() {
		Keyboard.enableRepeatEvents(true);
		ELEMENTS.add(title = new MCCGuiText(0, 4, 36, width - 8, null));
		ELEMENTS.add(members = new MCCGuiText(1, 4, 62, width - 8, null));
		members.valid = MCC.ALPHABET_NUMERIC + "_,";
		ELEMENTS.add(new MCCGuiButton(2, 4, 80, 50, "Create"));
	}
	
	@Override
	public void close() {
		Keyboard.enableRepeatEvents(false);
		super.close();
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		drawStringCentered("Create Group", width / 2, 10);
		drawString("Group Title:", 4, 26);
		drawString("Group Members:", 4, 52);
		drawString(MCCColour.GREY + "This can be changed later.", 100, 26);
		drawString(MCCColour.GREY + "Usernames separated by commas.", 100, 52);
		if (status != null)
			drawString(status, 56, 86);
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		super.clicked(clicked);
		if (clicked.id == 2) {
			if (title.getInput().length() <= 3) {
				status = MCCColour.RED_DARK + "Title too short!";
				return;
			}else if (!MCC.MCC.isLoggedIn()) {
				status = MCCColour.RED_DARK + "No connection to MineCC!";
				return;
			}else{
				status = MCCColour.GREY + "Creating Group...";
				clicked.enabled = false;
				MCC.MCC.send(new MCCPacketGroup(title.getInput(), members.getInput().split(",")));
			}
		}
	}

}
