package main;

import java.net.Socket;

import util.CustomTimer;
import util.PacketStream;

public class Account
{
	public PacketStream stream = new PacketStream();
	
	public int ping;
	
	public CustomTimer timer;//Depuis le dernier ping envoyé
	public Socket socket;
	
	public final Cube cube;
	
	public Account(String n, Socket s) 
	{
		socket = s;
		cube = new Cube(n);
	}
}
