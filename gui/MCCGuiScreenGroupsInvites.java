package co.minecc.client.gui;

import co.minecc.client.MCC;
import co.minecc.client.MCCColour;
import co.minecc.client.MCCGroupInvite;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;
import co.minecc.client.packets.MCCPacketGroupInvite;

public class MCCGuiScreenGroupsInvites extends MCCGuiScreen {

	public MCCGroupInvite invite;
	
	public MCCGuiButton buttonYes;
	public MCCGuiButton buttonNo;
	
	public MCCGuiScreenGroupsInvites(MCCGuiScreen p) {
		super(p);
	}

	@Override
	public void init() {
		ELEMENTS.add(buttonYes = new MCCGuiButton(0, (width / 2) - 60, (height / 2) - 10, 120,
				MCCColour.GREEN + "Join Group"));
		ELEMENTS.add(buttonNo = new MCCGuiButton(1, (width / 2) - 60, (height / 2) + 15, 120,
				MCCColour.RED_DARK + "Delete Invite"));
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		if (invite == null)
			invite = MCCGroupInvite.next();
		
		buttonYes.enabled = invite != null;
		buttonNo.enabled = buttonYes.enabled;
		super.draw(cursorX, cursorY);
		if (invite != null) {
			drawStringCentered(invite.FROM + " invited you to a Group!", width / 2, 40);
			drawStringCentered(invite.GROUP, width / 2, 50);
		}else{
			drawStringCentered("No invitations to display!", width / 2, 40);
			drawStringCentered("", width / 2, 50);
		}
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		super.clicked(clicked);
		if (clicked.id == buttonYes.id || clicked.id == buttonNo.id && invite != null) {
			MCC.MCC.send(new MCCPacketGroupInvite(invite.INVITE, clicked.id == buttonYes.id));
			invite.remove();
			invite = null;
		}
	}

}
