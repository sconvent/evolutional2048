package de.convent.evolutional2048.backend;

import Jama.Matrix;
import de.convent.evolutional2048.neuralNetwork.NeuralNetwork;
import de.convent.evolutional2048.util.Direction;

public class EvolutionPlayer
{
	NeuralNetwork neuralNetwork;

	public EvolutionPlayer(String path)
	{
		neuralNetwork = new NeuralNetwork(path);
	}

	public Direction move(int[] input)
	{
		double[] tmp = new double[input.length];
		for(int i = 0; i < input.length; i++)
			tmp[i] = input[i];
		return neuralNetwork.calculate(new Matrix(tmp, 16));
	}
}
