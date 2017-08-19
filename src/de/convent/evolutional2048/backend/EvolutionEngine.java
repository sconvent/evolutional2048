package de.convent.evolutional2048.backend;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.convent.evolutional2048.neuralNetwork.NeuralNetwork;

public class EvolutionEngine
{
	NeuralNetwork[] neuralNetwork;

	public EvolutionEngine()
	{
		neuralNetwork = new NeuralNetwork[100];
		for(int i = 0; i < 100; i++)
			neuralNetwork[i] = new NeuralNetwork(new int[] { 16, 16, 4 });
	}

	public void train()
	{

	}

	public void save(String path)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(neuralNetwork);
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
			neuralNetwork = (NeuralNetwork[]) in.readObject();
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
