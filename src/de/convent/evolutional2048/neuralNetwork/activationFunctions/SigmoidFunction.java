package de.convent.evolutional2048.neuralNetwork.activationFunctions;

import Jama.Matrix;

public class SigmoidFunction implements ActivationFunction
{
	@Override
	public double function(double value)
	{
		double tmp = Math.exp(value);
		return tmp / (tmp + 1);
	}
	
	@Override
	public Matrix function(Matrix values)
	{
		Matrix res = new Matrix(values.getArray());
		for(int i = 0; i < res.getArray().length; i++)
			for(int j = 0; j < res.getArray()[i].length; j++)
				res.getArray()[i][j] = function(res.getArray()[i][j]);
		return res;
	}
}
