package co.minecc.client.gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import co.minecc.client.MCCColour;
import co.minecc.client.gui.elements.MCCGuiSlider;
import co.minecc.client.gui.elements.MCCGuiSlider.MCCGuiSliderType;
import co.minecc.client.language.MCCLanguageText;

public class MCCGuiScreenChangelog extends MCCGuiScreen {

	protected static final ArrayList<String> CHANGELOG = new ArrayList<String>();
	
	static {
		final Thread THREAD = new Thread(new MCCGuiScreenChangelogDownload());
		THREAD.setName("MCCChangelog");
		THREAD.setPriority(Thread.MIN_PRIORITY);
		THREAD.start();
	}
	
	public MCCGuiSlider slider;
	
	public MCCGuiScreenChangelog(MCCGuiScreen p) {
		super(p);
	}
	
	@Override
	public void init() {
		ELEMENTS.add(slider = new MCCGuiSlider(0, width - 10, 10, height - 20, MCCGuiSliderType.VERTICAL));
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		drawStringCentered(MCCLanguageText.GUI_CHANGELOG.toString(), width / 2, 10, 0xFFFFFF);
		final String[] DATA = CHANGELOG.toArray(new String[]{});
		if (DATA.length >= 1){
			int scroll = (int) (slider.slide() * (DATA.length * 10));
			for (int i = 0; i != DATA.length; i++){
				int y = 30 + (i * 10) - scroll;
				if (y >= 25 && y <= height){
					drawString(DATA[i], 10, y, 0xFFFFFF);
				}
			}
		}else{
			drawStringCentered(MCCLanguageText.GUI_CHANGELOG_DOWNLOAD.toString(), width / 2, (height / 2) - 5, 0xFFFFFF);
		}
	}

}
class MCCGuiScreenChangelogDownload implements Runnable {

	@Override
	public void run() {
		try {
			final BufferedReader READER = new BufferedReader(new InputStreamReader(new URL(
					"https://dl.dropboxusercontent.com/u/37563436/MinecraftConnect/changelog.txt").openStream()));
			
			final ArrayList<String> CHANGELOG = new ArrayList<String>();
			
			while (READER.ready()){
				final String LINE = READER.readLine();
				if (LINE.startsWith(" ") && !LINE.startsWith("  -"))
					CHANGELOG.add((LINE.endsWith(":") ? MCCColour.CYAN_DARK : MCCColour.GREEN)+ LINE);
				else if (LINE.startsWith("  -"))
					CHANGELOG.add(MCCColour.CYAN + LINE);
				else if (LINE.startsWith("#"))
					CHANGELOG.add(MCCColour.GOLD + LINE.replaceAll("\\/", MCCColour.RED.toString()));
				else
					CHANGELOG.add(LINE);
					
			}
			MCCGuiScreenChangelog.CHANGELOG.clear();
			MCCGuiScreenChangelog.CHANGELOG.addAll(CHANGELOG);
			MCCGuiScreenChangelog.CHANGELOG.add(0, "");
			MCCGuiScreenChangelog.CHANGELOG.add(0, "You are using MineCC #" + co.minecc.client.MCC.VERSION + ".");
			READER.close();
		}catch (Exception e) {
			MCCGuiScreenChangelog.CHANGELOG.clear();
			MCCGuiScreenChangelog.CHANGELOG.add("Failed to download changelog!");
		}
	}
	
}
