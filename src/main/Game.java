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
			float temp=0;
			if ((c.direction & 0b1000) != 0)
				temp += dif * 0.01f;
			if ((c.direction & 0b100) != 0)
				temp -= dif * 0.01f;
			c.x += temp * Math.sin(c.angle);
			c.z -= temp * Math.cos(c.angle);
			temp=0;
			if ((c.direction & 0b10) != 0)
				temp -= dif * 0.01f;
			if ((c.direction & 0b1) != 0)
				temp += dif * 0.01f;
			double cos = Math.cos(c.angle), sin = Math.sin(c.angle);
			c.x += temp * cos;
			c.z += temp * sin;
			
			double max = (Math.abs(sin) + Math.abs(cos)) / 2;
			if (c.x > 10-max) c.x = 10-max;
			if (c.x < -10+max) c.x = -10+max;
			if (c.z > 10-max) c.z = 10-max;
			if (c.z < -10+max) c.z = -10+max;
		}
	}
}
