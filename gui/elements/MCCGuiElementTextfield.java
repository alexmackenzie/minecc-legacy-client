package co.minecc.client.gui.elements;

import org.lwjgl.input.Keyboard;

public abstract class MCCGuiElementTextfield extends MCCGuiElement {

	public boolean timestamp = false;
	public boolean selected = false;
	public boolean selectedLock = false;
	
	public int max = Integer.MAX_VALUE;
	public String valid = " abcdefghijklmnopqrstuvwxyz0123456789!\"\'\\£$%^&*()/<>?@#~;:.,|{}[]+-_=";
	
	public MCCGuiElementTextfield(long i, int x, int y, int w, int h) {
		super(i, x, y, w, h);
	}
	
	@Override
	public void clicked(MCCClick click, int cursorX, int cursorY) {
		if (!selectedLock && click == MCCClick.LEFT)
			selected = !selected;
		
		super.clicked(click, cursorX, cursorY);
	}
	
	@Override
	public void clickedOther(MCCClick click, int cursorX, int cursorY) {
		if (!selectedLock && click == MCCClick.LEFT)
			selected = false;
		
		super.clickedOther(click, cursorX, cursorY);
	}
	
	@Override
	public void typed(char typed, int key) {
		if (!selected)
			return;
		
		String input = getInput();
		if (typed == '\n' || typed == '\r') {
			sendInput();
			input = "";
		}else if (key == Keyboard.KEY_BACK) {
			if (input.length() > 0)
				input = input.substring(0, input.length() - 1);
		}else if (valid.contains(Character.toString(typed).toLowerCase()) && input.length() < max){
			input = input + typed;
		}
		setInput(input);
		super.typed(typed, key);
	}
	
	public abstract String getInput();
	public abstract String setInput(String i);
	public abstract void sendInput();

}
