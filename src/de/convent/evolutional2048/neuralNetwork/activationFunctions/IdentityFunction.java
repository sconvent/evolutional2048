package de.convent.evolutional2048.neuralNetwork.activationFunctions;

public class IdentityFunction implements ActivationFunction
{
	@Override
	public double function(double value)
	{
		return value;
	}
}
