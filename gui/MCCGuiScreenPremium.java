package co.minecc.client.gui;

import co.minecc.client.MCC;

public class MCCGuiScreenPremium extends MCCGuiScreenMessagePremium {

	private static final String[] MESSAGE_PREMIUM = new String[]{
		"You're a Premium User!",
		"Thanks for supporting MineCC!",
		"",
		"If you wish to support MineCC further you can make a donation, or " +
		"check out the awesome features you already have access to by visiting the " +
		"'Get Premium' website!"
	};
	
	private static final String[] MESSAGE_NONE = new String[]{
		"You're not currently a Premium User!",
		"",
		"If you like MineCC and wish to support development, you can donate to become " +
		"a Premium User, or simply donate to help us out! Every dollar is greatly appreciated!"
	};
	
	public MCCGuiScreenPremium(MCCGuiScreen p) {
		super(p, MCC.DATA.premium ? MESSAGE_PREMIUM : MESSAGE_NONE, true);
	}

}
