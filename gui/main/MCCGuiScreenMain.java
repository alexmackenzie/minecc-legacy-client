package co.minecc.client.gui.main;

import co.minecc.client.MCC;
import co.minecc.client.MCCColour;
import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;
import co.minecc.client.language.MCCLanguageText;
import co.minecc.client.packets.MCCPacketStatus;

public abstract class MCCGuiScreenMain extends MCCGuiScreen {

	public static final String LEFT = Character.toString('\u00BB') + Character.toString('\u00BB');
	public static final String RIGHT = Character.toString('\u00AB') + Character.toString('\u00AB');
	
	private static final int[] RAINBOW = new int[]{
		0xFF0000,
		0XFF7F00,
		0XFFFF00,
		0x7FFF00,
		0x00FF00,
		0x00FF7F,
		0x00FFFF,
		0x007FFF,
		0x0000FF,
		0x7F00FF,
		0xFF00FF,
		0xFF007F
	};
	
	private static int onlineCooldown = 0;
	
	public static void decreaseCooldown(){
		onlineCooldown--;
	}
	
	private int colour = 0;
	private long colourCycle = 0;
	
	private final String[] OPTIONS;
	private final int OPTIONS_LINE;
	
	public MCCGuiScreenMain(MCCGuiScreen p, String[] o, int oL) {
		super(p);
		OPTIONS = o;
		OPTIONS_LINE = Math.max(0, Math.min(oL, 3));
	}
	
	public MCCGuiButton buttonOnline;
	public MCCGuiButton buttonReconnect;
	
	@Override
	public void init() {
		ELEMENTS.add(buttonOnline = new MCCGuiButton(Integer.MAX_VALUE, width - 140, height - 60, 120, ""));
		ELEMENTS.add(buttonReconnect = new MCCGuiButton(Integer.MAX_VALUE - 1, buttonOnline.posX - 90, buttonOnline.posY, 80, MCCLanguageText.DISCONNECTED_RECONNECT));
		buttonReconnect.enabled = !MCC.MCC.isConnected();
		for (int i = 0; i != OPTIONS.length; i++){
			if (OPTIONS[i].length() <= 0)
				continue;
			int y = ((i / OPTIONS_LINE) * 30) + 28;
			int p = i % OPTIONS_LINE;
			int r = Math.min(OPTIONS.length - ((i / OPTIONS_LINE) * OPTIONS_LINE), OPTIONS_LINE);
			int x = -1;
			if (r == 3){
				if (p == 0){
					x = 16;
				}else if (p == 1){
					x = (width / 2) - 34;
				}else if (p == 2){
					x = width - 76;
				}
			}else if (r == 2){
				if (p == 0){
					x = (width / 2) - 74;
				}else if (p == 1){
					x = (width / 2) + 6;
				}
			}else if (r == 1){
				x = (width / 2) - 34;
			}
			ELEMENTS.add(new MCCGuiButton(i, x, y, 68, OPTIONS[i]));
		}
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		if (PARENT == null){
			drawString("MineCC #" + MCC.VERSION, 2, 2, 0x525252);
		}
		buttonOnline.enabled = onlineCooldown < 1;
		buttonReconnect.enabled = !MCC.MCC.isConnected();
		if (!buttonReconnect.enabled){
			if (MCC.MCC.isLoggedIn()){
				buttonReconnect.textRaw = MCCLanguageText.CONNECTED.toString();
			}else{
				buttonReconnect.textRaw = MCCLanguageText.CONNECTING.toString();
			}
		}else{
			buttonReconnect.textRaw = MCCLanguageText.DISCONNECTED_RECONNECT.toString();
		}
		if (MCC.MCC.isLoggedIn()){
			final long TIME = System.currentTimeMillis();
			if (TIME - colourCycle >= 1250){
				colourCycle = TIME;
				colour++;
				if (colour >= RAINBOW.length){
					colour = 0;
				}
			}
			drawStringCentered(LEFT + MCCColour.BOLD.toString() + " MineCC " + MCCColour.RESET.toString() + RIGHT, width / 2, 10, RAINBOW[colour]);
			if (MCC.DATA.online){
				buttonOnline.textRaw = MCCLanguageText.STATUS_ONLINE_YOU.toString();
			}else{
				buttonOnline.textRaw = MCCLanguageText.STATUS_INVISIBLE_YOU.toString();
			}
		}else{
			drawStringCentered(LEFT + MCCColour.BOLD.toString() + " MineCC " + MCCColour.RESET.toString() + RIGHT, width / 2, 10, 0xA0A0A0);
			buttonOnline.textRaw = MCCLanguageText.STATUS_OFFLINE_YOU.toString();
			buttonOnline.enabled = false;
		}
		drawStringCentered("Click to toggle status.", buttonOnline.posX + 60, buttonOnline.posY - 10, 0xC1C1C1);
		drawStringCentered(MCCColour.CYAN + MCC.NEWS[0], width / 2, height - 30, 0);
		drawStringCentered(MCCColour.CYAN + MCC.NEWS[1], width / 2, height - 20, 0);
		super.draw(cursorX, cursorY);
	}
	
	@Override
	public void clicked(MCCGuiElement element) {
		if (element.id < OPTIONS.length)
			option((int) element.id);
		if (element.equals(buttonOnline)){
			MCC.MCC.send(new MCCPacketStatus(!MCC.DATA.online));
			onlineCooldown = 7;
		}else if (element.equals(buttonReconnect)){
			MCC.MCC.connect(false);
		}
		super.clicked(element);
	}
	
	protected abstract void option(int i);

}
