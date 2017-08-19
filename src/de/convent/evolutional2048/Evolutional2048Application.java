package de.convent.evolutional2048;

import de.convent.evolutional2048.frontend.Frontend;

public class Evolutional2048Application
{
	public static void main(String[] args)
	{
		if(args.length == 1)
		{
			if(args[0].equals("evolution"))
				System.out.println("evolution");
			;
		}
		else
		{
			Frontend frontend = new Frontend();
		}
	}
}
