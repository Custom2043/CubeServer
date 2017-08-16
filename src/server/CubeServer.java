package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import main.Account;
import main.Main;
import packet.CPacketPlayerQuit;
import packet.CPacketServerClosed;
import packet.Packet;

public class CubeServer 
{
	public PlayerAccepter acc;
	public List<Account> connected = Collections.synchronizedList(new ArrayList<Account>());
	
	public CubeServer() throws IOException
	{
		acc = new PlayerAccepter(this);
	}
	public void sendPacketToAllPlayers(Packet p)
	{
		for (Account h : connected)
			p.send(h.socket);
	}
	public void sendPacketToAllPlayersExcept(Packet p, String name)
	{
		for (Account h : this.connected)
			if (!h.cube.name.equals(name))
				p.send(h.socket);
	}
	public void closeServer()
	{
		acc.quit();
		this.sendPacketToAllPlayers(new CPacketServerClosed());
		for (Iterator<Account> iter = this.connected.iterator();iter.hasNext();)
		{
			Account sa = iter.next();
			iter.remove();
			removePlayer(sa);
		}
	}
	public void removePlayer(Account pl)
	{
		System.out.println(pl.cube.name +" a quitté la partie");
		try {pl.socket.close();}
		catch (IOException e) {e.printStackTrace();}
		Main.server.sendPacketToAllPlayers(new CPacketPlayerQuit(pl.cube.name));
	}
	public void removePlayer(String name)
	{
		for (Iterator<Account> iter = this.connected.iterator();iter.hasNext();)
		{
			Account pl = iter.next();
			if (pl.cube.name.equals(name))
			{
				iter.remove();
				removePlayer(pl);
			}
		}
	}
	public Account getAccount(String nom)
	{
		for (Account h : this.connected)
			if (h.cube.name.equals(nom))
				return h;
		return null;
	}
	public void tick(long dif)
	{
		for (int i=0;i<this.connected.size();i++)
		{
			Account a = this.connected.get(i);
			if (a.timer.getDifference() > Main.TIMEOUT)
				{removePlayer(a.cube.name);System.out.println(a.cube.name + " A QUITTE LA PARTIE TABERNAK");}
			else 
			{
				Packet.read(a);
				Packet p;
				while ((p = Packet.readPacket(a.stream)) != null)
				{
					try
					{
						if (p instanceof Packet.PacketToServer)
						{
							if (p.toWrite())
								System.out.println("Server receive packet : "+p.getClass().getName());
							((Packet.PacketToServer)p).handleServer(a);
						}
					}
					catch(Exception e){e.printStackTrace();}
				}
			}
		}
	}
}
