package co.minecc.client.gui;

import co.minecc.client.MCC;
import co.minecc.client.MCCColour;
import co.minecc.client.MCCDesktop;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;
import co.minecc.client.language.MCCLanguageText;

public class MCCGuiScreenMessagePremium extends MCCGuiScreenMessage {

	public final boolean BUTTON;
	
	public MCCGuiScreenMessagePremium(MCCGuiScreen p, String[] msg, boolean b) {
		super(p, msg);
		BUTTON = b;
	}
	
	@Override
	public void init() {
		super.init();
		if (BUTTON) {
			MCCGuiButton button = new MCCGuiButton(8, (width / 2) - 160, height - 30, 100, MCCLanguageText.PREMIUM_GET);
			button.flash = true;
			ELEMENTS.add(button);
		}
		MCCGuiButton button = new MCCGuiButton(9, BUTTON ? (width / 2) + 60 : (width / 2) - 50,
					height - 30, 100, "Donate");
		button.flash = false;
		ELEMENTS.add(button);
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		drawStringCentered(MCCColour.CYAN + MCCLanguageText.PREMIUM.toString(), width / 2, 10);
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		if (clicked.id == 8 || clicked.id == 9) {
			MCCDesktop.openURL(clicked.id == 8 ? MCC.URL_PREMIUM : MCC.URL_DONATION);
			clicked.enabled = false;
		}
		super.clicked(clicked);
	}

}
