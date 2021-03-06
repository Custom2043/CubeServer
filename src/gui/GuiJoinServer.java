package gui;

import main.Account;
import client.ClientMain;
import util.ScreenCoor;

public class GuiJoinServer extends Gui
{
	public String name;
	public GuiJoinServer(String n)
	{
		this.boutons.add(new ZoneTexte("IP", 0, ScreenCoor.screenGui(.25f,.3f,.5f,0,0,-50,0,100), false, "IP"));
		this.boutons.add(new BasicBouton("Rejoindre le serveur", 1, ScreenCoor.screenGui(.375f,.7f,.25f,0,0,-50,0,100), false));
		name = n;
	}
	@Override
	public void quit() {
		
	}

	@Override
	protected void drawBeforeButtons() {
		
	}

	@Override
	protected void mouseEvent(int clicID, int X, int Y, boolean press,
			CustomBouton boutonOn)
	{
		if (clicID == 0 && press)
		{
			if (boutonOn != null && boutonOn.id == 1 && boutonOn.isActiv)
			{
				ClientMain.compte = new Account(name, null);
				ClientMain.joinDistantServer(boutons.get(0).getTexte().texte);
				ClientMain.setScreen(new GuiLoading());
			}
		}
	}

	@Override
	protected void keyboardEvent(char carac, int keyCode) {
		if (boutons.get(0).getTexte().texte.length() > 0 && !boutons.get(0).getTexte().texte.contains(" ") && boutons.get(0).getTexte().texte.length() < 15)
		{
			boutons.get(1).activ();
		}
		else
		{
			boutons.get(1).desactiv();
		}
		
	}

}
