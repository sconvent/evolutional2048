package de.convent.evolutional2048.backend;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import de.convent.evolutional2048.neuralNetwork.NeuralNetwork;

public class EvolutionEngine
{
	NeuralNetwork[] neuralNetworks;
	int[] averageScores;

	public EvolutionEngine()
	{
		neuralNetworks = new NeuralNetwork[100];
		averageScores = new int[100];
		for(int i = 0; i < 100; i++)
		{
			neuralNetworks[i] = new NeuralNetwork(new int[] { 16, 16, 4 });
			averageScores[i] = 0;
		}
	}

	public void train()
	{
		for(int evolution = 0; evolution < 10; evolution++)
		{
			List<NeuralNetwork> newNeuralNetworks = new ArrayList<>();
			for(int i = 0; i < 100; i++)
				for(int j = 0; j < 100; j++)
					newNeuralNetworks.addAll(EvolutionEngine.spawn(neuralNetworks[i], neuralNetworks[j]));
			
			List<Integer> newAverageScores = new ArrayList<>();
			for(int i = 0; i < newNeuralNetworks.size(); i++)
			{
				int totalScore = 0;
				for(int j = 0; j < 100; j++)
				{
					Game game = new Game();
					while(!game.hasGameEnded())
					{
						double[] tmp = new double[game.getTiles().length];
						for(int k = 0; k < game.getTiles().length; k++)
							tmp[k] = game.getTiles()[k];
						game.move(newNeuralNetworks.get(i).calculate(new Matrix(tmp, 16)));
					}
					totalScore += game.getScore();
				}
				newAverageScores.add(totalScore/100);
			}
			//TODO: extract best 100 nns
			//TODO: add randomness
		}
	}
	
	public static List<NeuralNetwork> spawn(NeuralNetwork parent1, NeuralNetwork parent2)
	{
		List<NeuralNetwork> res = new ArrayList<>();
		res.add(NeuralNetwork.merge(parent1, parent2, 0));
		res.add(NeuralNetwork.merge(parent1, parent2, .1));
		res.add(NeuralNetwork.merge(parent1, parent2, .2));
		res.add(NeuralNetwork.merge(parent1, parent2, .3));
		res.add(NeuralNetwork.merge(parent1, parent2, .4));
		res.add(NeuralNetwork.merge(parent1, parent2, .5));
		res.add(NeuralNetwork.merge(parent1, parent2, .6));
		res.add(NeuralNetwork.merge(parent1, parent2, .7));
		res.add(NeuralNetwork.merge(parent1, parent2, .8));
		res.add(NeuralNetwork.merge(parent1, parent2, .9));
		res.add(NeuralNetwork.merge(parent1, parent2, 1));
		return res;
	}

	public void save(String path)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(neuralNetworks);
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
			neuralNetworks = (NeuralNetwork[]) in.readObject();
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

	public void saveBest(String path)
	{
		int index = 0;
		for(int i = 0; i < neuralNetworks.length; i++)
			if(averageScores[i] > averageScores[index])
				index = i;
		neuralNetworks[index].save(path);
	}

	public NeuralNetwork[] getNeuralNetwork()
	{
		return neuralNetworks;
	}
}
