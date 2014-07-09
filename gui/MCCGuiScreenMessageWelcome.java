package co.minecc.client.gui;

import co.minecc.client.MCCColour;
import co.minecc.client.MCCSession;

public class MCCGuiScreenMessageWelcome extends MCCGuiScreenMessage {

	public MCCGuiScreenMessageWelcome(MCCGuiScreen p) {
		super(p, new String[]{
				"Hey there " + MCCSession.getUsername() + "!",
				"",
				"Looks like you're new to MineCC - thanks for checking it out! " +
				"MineCC, or MCC, aims to connect the Minecraft community by giving " +
				"Players all sorts of awesome features straight from their game!", "", "",
				"Chatting to friends, finding Servers and meeting new people has never been easier! " +
				"Just hit the F4 key and you'll automatically find yourself where you left off!", "", "",
				"We've designed everything to be as intuitive as possible, so you shouldn't have " +
				"any trouble working your way around!", "", "",
				"Thanks for trying MineCC, I hope you like it!",
				MCCColour.ITALIC + "~ alexmack929"
		});
	}

}
