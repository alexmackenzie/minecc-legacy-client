package co.minecc.client.gui;

import co.minecc.client.MCC;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;

public class MCCGuiScreenChoice extends MCCGuiScreen {

	public final String[] MESSAGE;
	public final String[] LABELS;
	public final int[] IDENTIFIERS;
	
	public MCCGuiScreenChoice(MCCGuiScreen p, String[] m, String[] l, int[] i) {
		super(p);
		MESSAGE = m;
		LABELS = l;
		IDENTIFIERS = i;
	}
	
	@Override
	public void init() {
		ELEMENTS.add(new MCCGuiButton(0, (width / 2) - 60, (height / 2) - 10, 120, LABELS[0]));
		ELEMENTS.add(new MCCGuiButton(1, (width / 2) - 60, (height / 2) + 15, 120, LABELS[1]));
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		for (int i = 0; i != MESSAGE.length; i++){
			drawStringCentered(MESSAGE[i], width / 2, ((height / 2) - 45) + (i * 10), 0xFFFFFF);
		}
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		if (clicked.id == 0 || clicked.id == 1){
			PARENT.choice(IDENTIFIERS[(int) clicked.id]);
			MCC.MCC.showGui(PARENT);
		}
		super.clicked(clicked);
	}

}
