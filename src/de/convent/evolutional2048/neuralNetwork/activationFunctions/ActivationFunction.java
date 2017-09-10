package de.convent.evolutional2048.neuralNetwork.activationFunctions;

import Jama.Matrix;

public interface ActivationFunction
{
	public double function(double value);
	public Matrix function(Matrix value);
}
