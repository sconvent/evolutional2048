package de.convent.evolutional2048.neuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;

import Jama.Matrix;
import de.convent.evolutional2048.neuralNetwork.activationFunctions.ActivationFunction;
import de.convent.evolutional2048.neuralNetwork.activationFunctions.StepFunction;
import de.convent.evolutional2048.util.Direction;

public class NeuralNetwork implements Serializable
{
	Matrix[] weights;
	int[] layerSizes;
	ActivationFunction activationFunction;
	float averageScore;

	public NeuralNetwork(int[] layerSizesInput)
	{
		layerSizes = layerSizesInput;
		weights = new Matrix[layerSizes.length - 1];
		for(int i = 0; i < layerSizes.length - 1; i++)
		{
			weights[i] = Matrix.random(layerSizes[i + 1], layerSizes[i]);
		}
	}

	public NeuralNetwork(String path)
	{
		load(path);
		activationFunction = new StepFunction();
	}

	public int[] getLayerSizes()
	{
		return layerSizes;
	}

	public Matrix[] getWeights()
	{
		return weights;
	}

	public float getAverageScore()
	{
		return averageScore;
	}

	public void setAverageScore(float averageScore)
	{
		this.averageScore = averageScore;
	}

	public Direction calculate(Matrix input)
	{
		Matrix current = input;
		ActivationFunction activationFunction = new StepFunction();
		for(int i = 0; i < weights.length; i++)
		{
			current = weights[i].times(current);
			current = activationFunction.function(current);
		}
		int maxIndex = 0;
		if(current.getArray()[1][0] > current.getArray()[maxIndex][0])
			maxIndex = 1;
		if(current.getArray()[2][0] > current.getArray()[maxIndex][0])
			maxIndex = 2;
		if(current.getArray()[3][0] > current.getArray()[maxIndex][0])
			maxIndex = 3;

		if(maxIndex == 0)
			return Direction.UP;
		else if(maxIndex == 1)
			return Direction.RIGHT;
		else if(maxIndex == 2)
			return Direction.DOWN;
		else
			return Direction.LEFT;
	}

	public void save(String path)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(weights);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in " + path);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void load(String path)
	{
		try
		{
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			weights = (Matrix[]) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
			return;
		}
		catch (ClassNotFoundException c)
		{
			c.printStackTrace();
			return;
		}
	}

	public void print()
	{
		DecimalFormat format = new DecimalFormat("#0.00");
		for(int n = 0; n < weights.length; n++)
		{
			for(int i = 0; i < weights[n].getArray().length; i++)
			{
				for(int j = 0; j < weights[n].getArray()[i].length; j++)
				{
					System.out.print(format.format(weights[n].getArray()[i][j]) + " ");
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}

	public static NeuralNetwork merge(NeuralNetwork nn1, NeuralNetwork nn2, double factor)
	{
		NeuralNetwork res = new NeuralNetwork(nn1.getLayerSizes());
		for(int i = 0; i < res.getWeights().length; i++)
		{
			for(int j = 0; j < res.getWeights().length; j++)
			{
				for(int k = 0; k < res.getWeights().length; k++)
				{
					res.getWeights()[i].getArray()[j][k] = nn1.getWeights()[i].getArray()[j][k] * factor
							+ nn2.getWeights()[i].getArray()[j][k] * (1 - factor);
				}
			}
		}
		return res;
	}
	
	private double getRandom()
	{
		if(Math.random() < .1)
			return Math.random()/5;
		else
			return 0;
	}

	public NeuralNetwork mutation()
	{
		NeuralNetwork res = new NeuralNetwork(getLayerSizes());
		for(int i = 0; i < res.getWeights().length; i++)
		{
			for(int j = 0; j < res.getWeights().length; j++)
			{
				for(int k = 0; k < res.getWeights().length; k++)
				{
					res.getWeights()[i].getArray()[j][k] = getWeights()[i].getArray()[j][k] + getRandom();
				}
			}
		}
		return res;
	}
}
