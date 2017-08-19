package de.convent.evolutional2048.neuralNetwork;

import Jama.Matrix;
import de.convent.evolutional2048.neuralNetwork.activationFunctions.ActivationFunction;

public class NeuralNetwork
{
	Matrix[] weights;
	ActivationFunction activationFunction;

	public NeuralNetwork(int[] layerSizes)
	{
		weights = new Matrix[layerSizes.length - 1];
		for(int i = 0; i < layerSizes.length - 1; i++)
		{
			weights[i] = Matrix.random(layerSizes[i], layerSizes[i + 1]);
		}
	}

	public Matrix calculate(Matrix input)
	{
		Matrix current = input;
		for(int i = 0; i < weights.length; i++)
		{
			current = weights[i].times(current);
		}
		return current;
	}
}
