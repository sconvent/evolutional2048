package de.convent.evolutional2048;

import de.convent.evolutional2048.backend.EvolutionEngine;
import de.convent.evolutional2048.frontend.Frontend;
import de.convent.evolutional2048.neuralNetwork.NeuralNetwork;

public class Evolutional2048Application
{
	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			if(args[0].equals("evolution"))
			{
				EvolutionEngine engine = new EvolutionEngine();
				engine.saveOne(args[1]);
			}
			else if(args[0].equals("print"))
			{
				NeuralNetwork network = new NeuralNetwork(args[1]);
				network.print();
			}
		}
		else
		{
			Frontend frontend = new Frontend();
		}
	}
}
