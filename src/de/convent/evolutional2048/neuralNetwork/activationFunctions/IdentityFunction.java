package de.convent.evolutional2048.neuralNetwork.activationFunctions;

import Jama.Matrix;

public class IdentityFunction implements ActivationFunction
{
	@Override
	public double function(double value)
	{
		return value;
	}
	
	@Override
	public Matrix function(Matrix values)
	{
		return values;
	}
}
