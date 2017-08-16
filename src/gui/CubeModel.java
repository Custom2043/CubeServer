package gui;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import util.QuadColor;
import util.TextureCoor;
import drawer.Model;
import drawer.VAOLoader;

public class CubeModel extends Model
{
	public CubeModel(float size, QuadColor c, Texture text)
	{
		super(24);
		this.texture = text;
		this.enableVertexArray(0);
		this.enableVertexArray(1);
		this.enableVertexArray(2);
		
		ByteBuffer buf = BufferUtils.createByteBuffer(this.vertexNumber * 12); // Position
		buf.putFloat(-size/2).putFloat(size/2).putFloat(-size/2); // Haut
		buf.putFloat(-size/2).putFloat(size/2).putFloat(size/2);
		buf.putFloat(size/2).putFloat(size/2).putFloat(size/2);
		buf.putFloat(size/2).putFloat(size/2).putFloat(-size/2);
		
		buf.putFloat(-size/2).putFloat(-size/2).putFloat(size/2); // Bas
		buf.putFloat(-size/2).putFloat(-size/2).putFloat(-size/2);
		buf.putFloat(size/2).putFloat(-size/2).putFloat(-size/2);
		buf.putFloat(size/2).putFloat(-size/2).putFloat(size/2);
		
		buf.putFloat(size/2).putFloat(size/2).putFloat(-size/2); // Arriere
		buf.putFloat(size/2).putFloat(-size/2).putFloat(-size/2);
		buf.putFloat(-size/2).putFloat(-size/2).putFloat(-size/2);
		buf.putFloat(-size/2).putFloat(size/2).putFloat(-size/2);
		
		buf.putFloat(-size/2).putFloat(size/2).putFloat(size/2); // Devant
		buf.putFloat(-size/2).putFloat(-size/2).putFloat(size/2);
		buf.putFloat(size/2).putFloat(-size/2).putFloat(size/2);
		buf.putFloat(size/2).putFloat(size/2).putFloat(size/2);
		
		buf.putFloat(-size/2).putFloat(size/2).putFloat(-size/2); // Gauche
		buf.putFloat(-size/2).putFloat(-size/2).putFloat(-size/2);
		buf.putFloat(-size/2).putFloat(-size/2).putFloat(size/2);
		buf.putFloat(-size/2).putFloat(size/2).putFloat(size/2);
		
		buf.putFloat(size/2).putFloat(size/2).putFloat(size/2); // Droite
		buf.putFloat(size/2).putFloat(-size/2).putFloat(size/2);
		buf.putFloat(size/2).putFloat(-size/2).putFloat(-size/2);
		buf.putFloat(size/2).putFloat(size/2).putFloat(-size/2);
		
		VAOLoader.storeBufferInAttributeList(0, 3, buf, GL11.GL_FLOAT);
		
		buf = BufferUtils.createByteBuffer(this.vertexNumber * 8); // Textures
		for (int i=0;i<vertexNumber/4;i++)
		{
			if (texture != null)
				for (float f : TextureCoor.allPicture.inFloatArray(texture))
					buf.putFloat(f);
			else
				for (int j=0;j<8;j++)
					buf.putFloat(-1);
		}
		VAOLoader.storeBufferInAttributeList(1, 2, buf, GL11.GL_FLOAT);
		
		buf = BufferUtils.createByteBuffer(this.vertexNumber * 4); // Textures
		for (int i=0;i<vertexNumber/4;i++)
		{
			for (Color cc : c.getAsColorArray())
			{
				buf.put((byte)cc.getRed()).put((byte)cc.getGreen()).put((byte)cc.getBlue()).put((byte)cc.getAlpha());
			}
		}
		VAOLoader.storeBufferInAttributeList(2, 4, buf, GL11.GL_UNSIGNED_BYTE);
	}
}
