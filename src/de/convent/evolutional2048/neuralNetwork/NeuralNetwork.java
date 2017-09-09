package de.convent.evolutional2048.neuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Jama.Matrix;
import de.convent.evolutional2048.neuralNetwork.activationFunctions.ActivationFunction;
import de.convent.evolutional2048.neuralNetwork.activationFunctions.StepFunction;
import de.convent.evolutional2048.util.Direction;

public class NeuralNetwork implements Serializable
{
	Matrix[] weights;
	ActivationFunction activationFunction;

	public NeuralNetwork(int[] layerSizes)
	{
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

	public Direction calculate(Matrix input)
	{
		Matrix current = input;
		for(int i = 0; i < weights.length; i++)
		{
			current = weights[i].times(current);
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
		else if(maxIndex == 0)
			return Direction.RIGHT;
		else if(maxIndex == 0)
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
		for(int i = 0; i < weights[0].getArray().length; i++)
		{
			for(int j = 0; j < weights[0].getArray()[i].length; j++)
			{
				System.out.print(weights[0].getArray()[i][j] + " ");
			}
			System.out.println("");
		}
	}
}
