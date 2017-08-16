package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import main.Account;
import main.Main;
import packet.CPacketJoinServer;
import packet.CPacketServerClosed;
import packet.DPacketPlayerConnect;
import packet.Packet;
import util.CustomTimer;
import util.PacketStream;

public class PlayerAccepter extends Thread
{
	public final CubeServer server;
	public final ServerSocket ss;
	public PlayerAccepter(CubeServer hs) throws IOException
	{
		this.server = hs;
		this.ss = new ServerSocket(Main.PORT);
		System.out.println(ss.getInetAddress());
		System.out.println(ss.getLocalPort());
		this.start();
	}
	public void run()
	{
		try
		{
			while (true)
				new ConnectWaiter(this.ss.accept(), this.server).start();
		}
		catch (IOException e)
		{
			System.out.println("PlayerAccepter ; Socket IO");
		}
	}
	public void quit()
	{
		try {this.ss.close();}
		catch (IOException e) {e.printStackTrace();}
	}
	public class ConnectWaiter extends Thread
	{
		public final CubeServer server;
		public final Socket socket;
		public ConnectWaiter(Socket s, CubeServer hs){this.socket = s;this.server = hs;}
		public void run()
		{
			try {
				this.socket.setReceiveBufferSize(Packet.MAX_SIZE);
				this.socket.setSendBufferSize(Packet.MAX_SIZE);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
			PacketStream ps = new PacketStream();
			DPacketPlayerConnect p = Packet.waitConnectPacket(this.socket, ps);
			if (p != null)
			{
				if (this.isPlayerAlreadyCo(p.acc.name))
				{
					System.out.println("PlayerAccepter : Le joueur "+p.acc.name+" est déjà  connecté");
					new CPacketServerClosed().send(this.socket);
				}
				else
				{
					Account h = new Account(p.acc.name, this.socket);
					h.stream = ps;
					h.timer = new CustomTimer();
					new CPacketJoinServer(this.server.connected).send(socket);
					this.server.connected.add(h);
					
					System.out.println("PlayerAccepter : Connexion de "+h.cube.name);
					this.server.sendPacketToAllPlayersExcept(new DPacketPlayerConnect(h.cube), h.cube.name);
				}
			}
			else
			{
				System.out.println("PlayerAccepter : Echec de la connexion");
				try {this.socket.close();}
				catch (IOException e) {e.printStackTrace();}
			}
		}
		public boolean isPlayerAlreadyCo(String nom)
		{
			for (Account h : this.server.connected)
				if (h.cube.name.equals(nom))
					return true;
			return false;
		}
	}
}
