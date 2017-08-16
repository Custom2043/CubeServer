package gui;

import main.Cube;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import packet.SPacketMovement;
import packet.SPacketRotation;
import util.Matrix;
import util.MouseHelper;
import client.ClientMain;
import drawer.CustomDrawer;

public class GuiIngame extends Gui
{
	public static float rotX;
	public GuiIngame()
	{
		MouseHelper.changeMouseGrabState();//grab mouse
	}
	@Override
	public void quit() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void drawBeforeButtons() 
	{
		CustomDrawer.load3D(70);
		Drawer.shader.start();
		Drawer.shader.loadProjectionMatrix(Matrix.createProjectionMatrix(70));
		for (Cube c : ClientMain.game.cubes)
			Drawer.drawCube(c);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Drawer.drawMur();
		CustomDrawer.load2D();
	}

	@Override
	protected void mouseEvent(int clicID, int X, int Y, boolean press, CustomBouton boutonOn) 
	{
		if (MouseHelper.grabbed)
		{
			ClientMain.compte.cube.angle += MouseHelper.getDXMouse() / 180f;
			rotX += MouseHelper.getDYMouse() / 180f;
			if (rotX > Math.PI/2)
				rotX = (float) (Math.PI/2);
			if (rotX < -Math.PI/2)
				rotX = -(float) (Math.PI/2);
			MouseHelper.update();
			new SPacketRotation(ClientMain.compte.cube).send(ClientMain.compte.socket);
		}
	}

	@Override
	protected void keyboardEvent(char carac, int keyCode) 
	{
		if (keyCode == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) //Pause
			MouseHelper.changeMouseGrabState();
		else
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

}
