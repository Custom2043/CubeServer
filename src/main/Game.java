package main;

import java.util.ArrayList;

public class Game 
{
	public ArrayList<Cube> cubes = new ArrayList<Cube>();
	public Cube getCube(String nom)
	{
		for (Cube c : this.cubes)
			if (c.name.equals(nom))
				return c;
		return null;
	}
	public void tick(long dif)
	{
		for (Cube c : cubes)
		{
			if ((c.direction & 0b1000) != 0)
				c.y += dif * 0.01f;
			if ((c.direction & 0b100) != 0)
				c.y -= dif * 0.01f;
			if ((c.direction & 0b10) != 0)
				c.x -= dif * 0.01f;
			if ((c.direction & 0b1) != 0)
				c.x += dif * 0.01f;
		}
	}
}
