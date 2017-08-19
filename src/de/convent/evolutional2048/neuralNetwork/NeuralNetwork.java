package de.convent.evolutional2048.neuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Jama.Matrix;
import de.convent.evolutional2048.neuralNetwork.activationFunctions.ActivationFunction;
import de.convent.evolutional2048.util.Direction;

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

	public NeuralNetwork(String path)
	{
		load(path);
	}

	public Direction calculate(Matrix input)
	{
		Matrix current = input;
		for(int i = 0; i < weights.length; i++)
		{
			current = weights[i].times(current);
		}
		int maxIndex = 0;
		if(current.getArray()[0][1] > current.getArray()[0][maxIndex])
			maxIndex = 1;
		if(current.getArray()[0][2] > current.getArray()[0][maxIndex])
			maxIndex = 2;
		if(current.getArray()[0][3] > current.getArray()[0][maxIndex])
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
			System.out.printf("Serialized data is saved in " + path);
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
}
