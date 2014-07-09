package co.minecc.client.gui;

import org.lwjgl.input.Keyboard;

import co.minecc.client.MCC;
import co.minecc.client.MCCChat;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiChat;
import co.minecc.client.gui.elements.MCCGuiElement;

public class MCCGuiScreenChat extends MCCGuiScreen {
	
	public static void open(MCCChat c) {
		MCC.MCC.showGui(new MCCGuiScreenChat(MCCGuiScreen.active(), c));
	}
	
	private static MCCChat chatOpen;
	
	public MCCGuiChat chatElement;
	
	public MCCGuiButton[] button;
	public MCCChat[] buttonChats;
	
	public MCCGuiScreenChat(MCCGuiScreen p) {
		super(p);
	}
	
	public MCCGuiScreenChat(MCCGuiScreen p, MCCChat c) {
		this(p);
		if (chatOpen != null)
			chatOpen.setShowing(false);
		chatOpen = c;
		chatOpen.bump();
	}
	
	@Override
	public void init() {
		Keyboard.enableRepeatEvents(true);
		int buttonCount = (height - (30 + 10)) / 22;
		button = new MCCGuiButton[buttonCount];
		for (int i = 0; i != buttonCount; i++){
			ELEMENTS.add(button[i] = new MCCGuiButton(i, 6, 30 + (22 * i), 129, ""));
		}
		ELEMENTS.add(chatElement = new MCCGuiChat(-1, 136, 28, width - 132, height - 32, null));
		chatElement.width = width - chatElement.posX;
		chatElement.selected = true;
		chatElement.selectedLock = true;
		update();
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		update();
		super.draw(cursorX, cursorY);
	}
	
	@Override
	public void close() {
		Keyboard.enableRepeatEvents(false);
		super.close();
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		super.clicked(clicked);
		if (clicked.id >= 0 && clicked.id < 128) {
			if (chatOpen != null)
				chatOpen.setShowing(false);
			chatOpen = buttonChats[(int) clicked.id];
		}
		update();
	}
	
	public void update() {
		buttonChats = new MCCChat[button.length];
		for (int i = 0; i != button.length; i++){
			final MCCChat CHAT = MCCChat.getChatsIndex(i);
			buttonChats[i] = CHAT;
			button[i].enabled = false;
			button[i].textRaw = "";
			if (CHAT != null){
				button[i].enabled = true;
				button[i].textRaw = CHAT.getTitle();
				button[i].flash = CHAT.getFlash();
			}
		}
		chatElement.chat = chatOpen;
	}
	
}
