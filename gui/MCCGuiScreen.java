package co.minecc.client.gui;

import java.util.ArrayList;

import co.minecc.client.MCC;
import co.minecc.client.gui.elements.MCCClick;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;
import co.minecc.client.language.MCCLanguageText;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public abstract class MCCGuiScreen extends MCCGui {

	public static boolean showing(Class<? extends MCCGuiScreen> type) {
		if (MCC.MINECRAFT.currentScreen instanceof MCCGuiScreenNative)
			return type.isInstance(active);
		else
			return false;
	}
	
	public static MCCGuiScreen active() {
		return active;
	}
	
	public static MCCGuiScreenNative activeNative() {
		return activeNative;
	}
	
	public static void init(MCCGuiScreen s) {
		active = s;
		if (Minecraft.getMinecraft().currentScreen instanceof MCCGuiScreenNative)
			MCC.MCC.showGuiMinecraft(activeNative);
	}
	
	private static MCCGuiScreen active;
	private static MCCGuiScreenNative activeNative;
	
	public static class MCCGuiScreenNative extends GuiScreen {
		
		public FontRenderer fontRenderer;
		
		public MCCGuiScreenNative(){
			activeNative = this;
		}
		
		@Override
		public void drawScreen(int par1, int par2, float par3) {
			activeNative = this;
			fontRenderer = fontRendererObj;
			super.drawScreen(par1, par2, par3);
			drawDefaultBackground();
			active.draw(par1, par2);
		}
		
		@Override
		public void initGui() {
			active.ELEMENTS.clear();
			active.initNative();
			active.init();
			super.initGui();
		}
		
		@Override
		protected void mouseClicked(int par1, int par2, int par3) {
			boolean clicked = false;
			MCCClick click = MCCClick.get(par3);
			for (final MCCGuiElement E : active.ELEMENTS.toArray(new MCCGuiElement[]{})){
				if (E.encompasses(par1, par2) && !clicked){
					active.clicked(E);
					E.clicked(click, par1, par2);
					clicked = true;
				}else{
					E.clickedOther(click, par1, par2);
				}
			}
		}
		
		@Override
		protected void keyTyped(char par1, int par2) {
			for (final MCCGuiElement E : active.ELEMENTS.toArray(new MCCGuiElement[]{})){
				E.typed(par1, par2);
			}
			active.key(par1, par2);
		}
		
		public final void bind(ResourceLocation l){
			mc.getTextureManager().bindTexture(l);
		}
		
		@Override
		public void onGuiClosed() {
			for (MCCGuiElement e : active.ELEMENTS)
				e.closed();
			active.close();
			super.onGuiClosed();
		}
		
	}
	
	public int width = 0, height = 0;
	
	public final MCCGuiScreen PARENT;
	public final ArrayList<MCCGuiElement> ELEMENTS;
	
	public MCCGuiScreen(MCCGuiScreen p) {
		PARENT = p;
		ELEMENTS = new ArrayList<MCCGuiElement>();
	}
	
	private static final long BACK = Long.MIN_VALUE + 0xBACC;
	
	public abstract void init();
	
	public void close() {};
	public void choice(int id) {};
	
	public void clicked(MCCGuiElement clicked) {
		if (clicked.id == BACK){
			MCC.MCC.showGui(PARENT);
		}
	}
	
	public final void initNative(){
		if (PARENT != null){
			ELEMENTS.add(new MCCGuiButton(BACK, 4, 4, 40, MCCLanguageText.GUI_BACK));
		}
		width = activeNative.width;
		height = activeNative.height;
	}
	
	public void draw(int cursorX, int cursorY){
		for (final MCCGuiElement E : ELEMENTS.toArray(new MCCGuiElement[]{})){
			E.drawInit(this, cursorX, cursorY);
			E.draw(this, cursorX, cursorY);
		}
	}
	
	public void drawString(String s, int x, int y, int c){
		activeNative.drawString(activeNative.fontRenderer, s, x, y, c);
	}
	
	public void drawString(String s, int x, int y){
		this.drawString(s, x, y, 0xFFFFFF);
	}
	
	public void drawStringCentered(String s, int x, int y, int c){
		activeNative.drawCenteredString(activeNative.fontRenderer, s, x, y, c);
	}
	
	public void drawStringCentered(String s, int x, int y){
		this.drawStringCentered(s, x, y, 0xFFFFFF);
	}
	
	public void key(char c, int k) {}
	
}
