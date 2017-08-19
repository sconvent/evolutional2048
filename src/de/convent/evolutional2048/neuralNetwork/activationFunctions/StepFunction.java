package de.convent.evolutional2048.neuralNetwork.activationFunctions;

public class StepFunction implements ActivationFunction
{
	@Override
	public double function(double value)
	{
		if(value > 0)
			return value;
		else
			return 0;
	}
}
