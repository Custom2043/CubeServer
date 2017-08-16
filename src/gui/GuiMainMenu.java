package gui;

import client.ClientMain;
import main.Account;
import util.ScreenCoor;

public class GuiMainMenu extends Gui
{
	public GuiMainMenu()
	{
		this.boutons.add(new ZoneTexte("Pseudo", 0, ScreenCoor.screenGui(.25f,.25f,.5f,0,0,-50,0,100), true, "Pseudo"));
		this.boutons.add(new BasicBouton("Créer un serveur", 1, ScreenCoor.screenGui(.375f,.5f,.25f,0,0,-50,0,100), false));
		this.boutons.add(new BasicBouton("Rejoindre le serveur", 2, ScreenCoor.screenGui(.375f,.75f,.25f,0,0,-50,0,100), false));
	}
	@Override
	public void quit(){}
	@Override
	public void drawBeforeButtons()
	{
	}

	@Override
	protected void keyboardEvent(char carac, int keyCode) 
	{
		if (boutons.get(0).getTexte().texte.length() > 0 && !boutons.get(0).getTexte().texte.contains(" ") && boutons.get(0).getTexte().texte.length() < 15)
		{
			boutons.get(1).activ();
			boutons.get(2).activ();
		}
		else
		{
			boutons.get(1).desactiv();
			boutons.get(2).desactiv();
		}
	}

	@Override
	protected void mouseEvent(int clicID, int X, int Y, boolean press, CustomBouton boutonOn)
	{
		if (clicID == 0 && press)
		{
			if (boutonOn != null && boutonOn.id == 1 && boutonOn.isActiv)
			{
				ClientMain.compte = new Account(boutons.get(0).getTexte().texte, null);
				ClientMain.createServer();
				ClientMain.setScreen(new GuiLoading());
			}
			if (boutonOn != null && boutonOn.id == 2 && boutonOn.isActiv)
			{
				ClientMain.setScreen(new GuiJoinServer());
			}
		}
	}
}
