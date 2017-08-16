package client;

import gui.Drawer;
import gui.Gui;
import gui.GuiIngame;
import gui.GuiMainMenu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import main.Account;
import main.Game;
import main.Main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import packet.DPacketPing;
import packet.DPacketPlayerConnect;
import packet.Packet;
import packet.SPacketQuitGame;
import server.CubeServer;
import util.CustomTimer;
import util.TimeSection;
import drawer.CustomDrawer;
import drawer.ShaderProgram;

public class ClientMain extends Main
{
	private static Gui screen;
	private static boolean isServer = false;
	public static Game game;
	public static Account compte;
	
	public static int fps, prevFps=0;
	public static CustomTimer fpsCounter = new CustomTimer();
	
	public static void main(String[] args) throws LWJGLException
	{
		new ClientMain().run();
	}
	public boolean admin(){return false;}
	protected ClientMain() throws LWJGLException 
	{
		Keyboard.enableRepeatEvents(true);
		CustomDrawer.createDisplay(1280, 720, gameName);
		screen = new GuiMainMenu();
		CustomDrawer.load2D();
	}
	public void quit() 
	{
		if (ingame())
			quitServer(true);
		screen.quit();
		ShaderProgram.quit();
		Drawer.text.quit();
	}
	public static void startNewGame()
	{
		game = new Game();
		setScreen(new GuiIngame());
	}
	public static void quitServer(boolean send)
	{
		if (ClientMain.isServer)
		{
			Main.server.closeServer();
			Main.server = null;
			ClientMain.isServer = false;
			compte.socket = null;
		} 
		else
		{
			if (send)
				new SPacketQuitGame().send(compte.socket);
			try {compte.socket.close();}
			catch (IOException e) {e.printStackTrace();}
			compte.socket = null;
		}
		setScreen(new GuiMainMenu());
	}
	@Override
	public void tick(long dif) 
	{
		fps ++;
		if (fpsCounter.getDifference() >= 1000)
		{
			fpsCounter.set0();
			prevFps = fps;
			fps = 0;
			TimeSection.setLast();
		}
		TimeSection.beginSection(TimeSection.PACKET);
		if (compte != null && compte.socket != null)
		{
			Packet.read(compte);
			Packet p;
			while ((p = Packet.readPacket(compte.stream)) != null)
			{
				try
				{
					if (p instanceof Packet.PacketToClient)
					{
						if (p.toWrite())
							System.out.println("Client receive packet : "+p.getClass().getName());
						((Packet.PacketToClient)p).handleClient();
					}
				}
				catch(Exception e){e.printStackTrace();}
			}
		}
		if (ingame())
		{
			if (compte.timer.getDifference() > TIMEOUT)//Server Timeout
				{quitServer(false);System.out.println("SERVEUR TIMEOUT");}
			else
			{
				if (compte.timer.getDifference() > PINGPERIOD)
				{
					new DPacketPing().send(compte.socket);
					compte.timer.set0();
				}
			}
			game.tick(dif);
		}
		
		if (Display.isCloseRequested())
			continu = false;
		
		TimeSection.beginSection(TimeSection.CLIENT_LOGIC);
		while (Mouse.next())
			screen.click();
		
		while (Keyboard.next())
			if (Keyboard.getEventKey() == Keyboard.KEY_F3 && Keyboard.getEventKeyState())
				Gui.debugMode = !Gui.debugMode;
			else
				screen.type();
		
		TimeSection.beginSection(TimeSection.RENDER);
		screen.draw();
		TimeSection.beginSection(TimeSection.UPDATE);
		CustomDrawer.updateScreenSize();
		CustomDrawer.update();
		TimeSection.beginSection(TimeSection.CLIENT_LOGIC);
	}
	public static void setScreen(Gui s)
	{
		screen.quit();
		screen = s;
	}
	public static boolean ingame()
	{
		return screen instanceof GuiIngame;
	}
	//Means that this instance of the game has a GUI
	protected boolean isClient(){return true;}
	//This instance of the game is still a client, but also runs a server
	public static void createServer()
	{
		isServer = true;
		try
		{
			Main.server = new CubeServer();
			compte.socket = new Socket("localhost", Main.PORT);
			compte.socket.setReceiveBufferSize(Packet.MAX_SIZE);
			compte.socket.setSendBufferSize(Packet.MAX_SIZE);
			new DPacketPlayerConnect(compte.cube).send(compte.socket);
		}
		catch (IOException  e) {e.printStackTrace();System.out.println("BattleTube ; Can't create Server");return;}
	}
	public static void joinDistantServer(String address)
	{
		isServer = false;
		try
		{
			compte.socket = new Socket(InetAddress.getByName(address), Main.PORT);
			compte.socket.setReceiveBufferSize(Packet.MAX_SIZE);
			compte.socket.setSendBufferSize(Packet.MAX_SIZE);
			new DPacketPlayerConnect(compte.cube).send(compte.socket);
		}
		catch (IOException  e) {e.printStackTrace();System.out.println("BattleTube ; Can't connect to Server");return;}
	}
}