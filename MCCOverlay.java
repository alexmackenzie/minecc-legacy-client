package co.minecc.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class MCCOverlay extends GuiAchievement {

	private final Minecraft MC;
	
	private static final ResourceLocation window = new ResourceLocation("textures/gui/achievement/achievement_background.png");
	private int windowWidth, windowHeight;
	
	private long notificationUnix;
	private String notificationTitle;
	private String notificationData;
	
	public MCCOverlay(Minecraft par1Minecraft) {
		super(par1Minecraft);
		MC = par1Minecraft;
	}
	
	private void updateWindowScale() {
        GL11.glViewport(0, 0, MC.displayWidth, MC.displayHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        windowWidth = MC.displayWidth;
        windowHeight = MC.displayHeight;
        ScaledResolution var1 = new ScaledResolution(MC.gameSettings, MC.displayWidth, MC.displayHeight);
        windowWidth = var1.getScaledWidth();
        windowHeight = var1.getScaledHeight();
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, (double)windowWidth, windowHeight, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
    }
	
	@Override
	/**
	 * Draw Window.
	 */
	public void func_146254_a() {
		if (notificationTitle != null && notificationUnix != 0L)
        {
            double var1 = (double)(Minecraft.getSystemTime() - notificationUnix) / 7500.0D;
            if (var1 < 0.0D || var1 > 1.0D)
            {
                notificationUnix = 0L;
            }
            else
            {
                updateWindowScale();
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
                double var3 = var1 * 2.0D;

                if (var3 > 1.0D)
                {
                    var3 = 2.0D - var3;
                }

                var3 *= 4.0D;
                var3 = 1.0D - var3;

                if (var3 < 0.0D)
                {
                    var3 = 0.0D;
                }

                var3 *= var3;
                var3 *= var3;
                int var5 = windowWidth - 160;
                int var6 = 0 - (int)(var3 * 36.0D);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                MC.getTextureManager().bindTexture(window);
                GL11.glDisable(GL11.GL_LIGHTING);
                this.drawTexturedModalRect(var5, var6, 96, 202, 160, 32);
                MC.fontRenderer.drawString(notificationTitle, var5 + 4, var6 + 7, 0x7FFFD4);
                MC.fontRenderer.drawString(notificationData, var5 + 4, var6 + 18, -1);
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glEnable(GL11.GL_COLOR_MATERIAL);
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }
		MCC.MCC.onTick();
		if (MC.currentScreen == null ||
				MC.currentScreen instanceof GuiInventory ||
				MC.currentScreen instanceof GuiChat ||
				MC.currentScreen instanceof GuiIngameMenu) MCCUI.draw();
	}
	
	public void notification(String t, String d) {
		notificationUnix = Minecraft.getSystemTime();
		if (t != null){
			notificationTitle = t.trim();
		}else{
			notificationTitle = "";
		}
		if (d != null){
			notificationData = d.trim();
		}else{
			notificationData = "";
		}
	}

}
