package gui;

import main.Cube;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import util.Matrix;
import util.QuadColor;
import drawer.CustomDrawer;
import drawer.TextureManager;

public class Drawer 
{
	public static TextureManager text = new TextureManager("", "PNG");
	private static Texture TEXTURE = text.loadTexture("res/Texture.png"),
						   MUR = text.loadTexture("res/mur.png");
	private static CubeModel cubeModel = new CubeModel(1, new QuadColor(), TEXTURE);
	private static CubeModel mur = new CubeModel(20, new QuadColor(new Color(1, 1, 1, 0.5f)), MUR);
	public static CubeShader shader = new CubeShader("res/DefaultVertex.txt", "res/DefaultFragment.txt");
	public static void drawCube(Cube c)
	{
		shader.loadViewMatrix(Matrix.createTransformationMatrix(c.x, 0, c.z, c.angle));
		CustomDrawer.drawModel(cubeModel);
	}
	public static void drawMur()
	{
		shader.loadViewMatrix(Matrix.createTransformationMatrix(0, 9, 0, 0));
		CustomDrawer.drawModelPart(mur, 8, 16);
	}
}
