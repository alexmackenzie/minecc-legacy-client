package co.minecc.client.gui;

import co.minecc.client.MCC;
import co.minecc.client.MCCColour;
import co.minecc.client.MCCDesktop;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;

public class MCCGuiScreenOutdated extends MCCGuiScreenMessage {

	public String message;
	
	public MCCGuiScreenOutdated(MCCGuiScreen p) {
		super(p, new String[]{
				"Looks like you're using an outdated version of MineCC.",
				p == null ? "Unfortunately this means you'll have to update in order to use it!"
						: "Luckily backwards compatability is offered, but some features " +
								"probably won't work properly - updating is highly recommended."
		});
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		drawStringCentered(MCCColour.RED.toString() + MCCColour.BOLD.toString() + "Outdated Client!", width / 2, 10, 0);
		drawStringCentered(MCC.URL_UPDATE, width / 2, 100 + 22, 0xFFFFFF);
		if (PARENT == null) {
			drawStringCentered("Press F4 to exit...", width / 2, height - 20, 0xA0A0A0);
		}
	}
	
	@Override
	public void init() {
		MCCGuiButton button;
		ELEMENTS.add(button = new MCCGuiButton(0, (width / 2) - 50, 100, 100, "Open Update URL"));
		button.flash = true;
	}
	
	@Override
	public void clicked(MCCGuiElement element) {
		if (element.id == 0 && element.enabled){
			MCCDesktop.openURL(MCC.URL_UPDATE);
			element.enabled = false;
		}
		super.clicked(element);
	}

}
