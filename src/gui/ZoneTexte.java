package gui;

import org.newdawn.slick.AngelCodeFont;

import util.ScreenCoor;

public class ZoneTexte extends BasicZoneTexte
{
	public ZoneTexte(String n, int i, ScreenCoor c, boolean isActiv) 
	{
		super(n, i, c, "", isActiv);
	}

	public ZoneTexte(String n, int i, ScreenCoor c, boolean isActiv, String s) 
	{
		super(n, i, c, s, isActiv);
	}
	
	public AngelCodeFont getFont() {
		return BasicBouton.font;
	}
}
