package gui;

import drawer.Matrix3DShader;

public class CubeShader extends Matrix3DShader
{

	public CubeShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "coor");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "color");
	}
}
