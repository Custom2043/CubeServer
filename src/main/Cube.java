package main;

public class Cube 
{
	public final String name;
	public double x=0, y=0;
	public byte direction = 0;
	public Cube(String n)
	{
		name = n;
	}
	public Cube(String n, double xx, double yy)
	{
		name = n;x = xx;y = yy;
	}
}