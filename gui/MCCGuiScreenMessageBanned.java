package co.minecc.client.gui;

import co.minecc.client.MCCColour;
import co.minecc.client.language.MCCLanguageText;

public class MCCGuiScreenMessageBanned extends MCCGuiScreenMessage {

	public final String DATE, REASON, ADMIN, UNBAN;
	
	public MCCGuiScreenMessageBanned(MCCGuiScreen p, String d, String r, String a, String u) {
		super(p, new String[]{
				MCCColour.RED + "You are banned from MineCC!", "", "",
				"You were banned by " + a + " for \"" + r + "\", at " + d + ".",
				"",
				(u == null ? "You'll be automatically unbanned in " + u + "." : "This ban is permanent and will not " +
						"automatically be removed."),
						"",
						MCCLanguageText.GUI_BANNED_MISTAKE.toString(),
						MCCLanguageText.GUI_BANNED_CONTACT.toString()
		});
		DATE = d;
		REASON = r;
		ADMIN = a;
		UNBAN = u;
	}
	
}
