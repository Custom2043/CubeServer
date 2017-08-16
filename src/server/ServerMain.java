package server;

import java.io.IOException;

import main.Main;

import org.lwjgl.LWJGLException;

public class ServerMain extends Main
{
	public static void main(String[] args) throws LWJGLException, IOException
	{
		new ServerMain().run();
	}
	
	public ServerMain() throws IOException
	{
		super();
		Main.server = new CubeServer();
	}
	@Override
	protected void tick(long dif) 
	{
		Main.server.tick(dif);
	}

	@Override
	protected void quit() 
	{
		Main.server.closeServer();
	}

	@Override
	protected boolean isClient() 
	{
		return false;
	}

}
