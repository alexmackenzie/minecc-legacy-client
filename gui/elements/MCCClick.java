package co.minecc.client.gui.elements;

import org.lwjgl.input.Mouse;

public enum MCCClick {
	LEFT,
	RIGHT,
	MIDDLE,
	
	UNKNOWN;
	
	public static MCCClick get(int id){
		if (id == 0)
			return LEFT;
		else if (id == 1)
			return RIGHT;
		else if (id == 2)
			return MIDDLE;
		else
			return UNKNOWN;
	}
	
	public static boolean held(MCCClick click){
		if (click == LEFT)
			return Mouse.isButtonDown(0);
		else if (click == RIGHT)
			return Mouse.isButtonDown(1);
		else if (click == MIDDLE)
			return Mouse.isButtonDown(2);
		return false;
	}
	
}
