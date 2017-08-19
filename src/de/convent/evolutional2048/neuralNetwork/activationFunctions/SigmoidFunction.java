package de.convent.evolutional2048.neuralNetwork.activationFunctions;

public class SigmoidFunction implements ActivationFunction
{
	@Override
	public double function(double value)
	{
		double tmp = Math.exp(value);
		return tmp / (tmp + 1);
	}
}
