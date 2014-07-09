package co.minecc.client.gui;

import co.minecc.client.MCCDesktop;
import co.minecc.client.MCCSession;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;

public class MCCGuiScreenSignatures extends MCCGuiScreenMessage {

	public MCCGuiScreenSignatures(MCCGuiScreen p) {
		super(p, new String[]{
				"Since MCC #4, MineCC has offered live status signatures for free. " + 
		"These signatures simply consist of a URL to a .png image, which will update according " + 
						"to your Online/Offline/Invisible status. You can use these wherever you " + 
		"like to let people that if you're using MCC and if you're on at the moment!"
		});
	}
	
	@Override
	public void init() {
		ELEMENTS.add(new MCCGuiButton(0, 40, height - 25, 100, "Open Signature"));
		ELEMENTS.add(new MCCGuiButton(1, width - 140, height - 25, 100, "Open Mini Signature"));
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		drawStringCentered("Signatures", width / 2, 10, 0xFFFFFF);
		drawStringCentered("Click the buttons below to", width / 2, height - 75, 0xFFFFFF);
		drawStringCentered("access your live signatures!", width / 2, height - 65, 0xFFFFFF);
	}
	
	@Override
	public void clicked(MCCGuiElement element) {
		if (element.id == 0){
			MCCDesktop.openURL("http://signature.minecraftconnect.co/o/" + MCCSession.getUsername() + ".png");
		}else if (element.id == 1){
			MCCDesktop.openURL("http://signature.minecraftconnect.co/o/s/" + MCCSession.getUsername() + ".png");
		}
		element.enabled = !(element.id >= 0);
		super.clicked(element);
	}

}
