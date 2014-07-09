package co.minecc.client.gui.main;

import co.minecc.client.MCC;
import co.minecc.client.gui.MCCGuiScreenChangelog;
import co.minecc.client.gui.MCCGuiScreenChat;
import co.minecc.client.gui.MCCGuiScreenFriends;
import co.minecc.client.gui.MCCGuiScreenGroups;
import co.minecc.client.gui.MCCGuiScreenPremium;

public class MCCGuiScreenMainBase extends MCCGuiScreenMain {

	public MCCGuiScreenMainBase() {
		super(null, new String[]{
				"Friends",
				"Chat",
				"Changelog",
				"Premium",
				"Groups"
		}, 2);
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
	}

	@Override
	protected void option(int i) {
		if (i == 0)
			MCC.MCC.showGui(new MCCGuiScreenFriends(this));
		else if (i == 1)
			MCC.MCC.showGui(new MCCGuiScreenChat(this));
		else if (i == 2)
			MCC.MCC.showGui(new MCCGuiScreenChangelog(this));
		else if (i == 3)
			MCC.MCC.showGui(new MCCGuiScreenPremium(this));
		else if (i == 4)
			MCC.MCC.showGui(new MCCGuiScreenGroups(this));
	}

}
