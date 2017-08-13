package gui;

import main.ClientMain;
import main.Cube;

import org.lwjgl.input.Keyboard;

import packet.SPacketMovement;
import drawer.CustomDrawer;

public class GuiIngame extends Gui
{

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void drawBeforeButtons() 
	{
		CustomDrawer.load3D(70);
		for (Cube c : ClientMain.game.cubes)
			CustomDrawer.drawCube(1, 1, 1, c.x-.5, -.5, c.y-5,0, 0, 0);
		CustomDrawer.load2D();
	}

	@Override
	protected void mouseEvent(int clicID, int X, int Y, boolean press, CustomBouton boutonOn) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void keyboardEvent(char carac, int keyCode) 
	{
		byte tempDir = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			tempDir += 0b1000;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			tempDir += 0b100;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			tempDir += 0b10;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			tempDir += 0b1;
		if (tempDir != ClientMain.compte.cube.direction)
		{
			ClientMain.compte.cube.direction = tempDir;
			new SPacketMovement(ClientMain.compte.cube).send(ClientMain.compte.socket);
		}
	}

}
