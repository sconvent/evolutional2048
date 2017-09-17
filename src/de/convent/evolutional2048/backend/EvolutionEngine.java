package de.convent.evolutional2048.backend;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Jama.Matrix;
import de.convent.evolutional2048.neuralNetwork.NeuralNetwork;

public class EvolutionEngine
{
	List<NeuralNetwork> neuralNetworks;

	public EvolutionEngine()
	{
		neuralNetworks = new ArrayList<>();
		for(int i = 0; i < 100; i++)
		{
			NeuralNetwork newNeuralNetwork = new NeuralNetwork(new int[] { 16, 16, 2 });
			newNeuralNetwork.setAverageScore(0);
			neuralNetworks.add(newNeuralNetwork);
		}
	}

	public void train()
	{
		for(int evolution = 0; evolution < 10; evolution++)
		{
			System.out.println("Generation" + evolution);
			//for(int i = 0; i < 10; i++)
			//	for(int j = 0; j < 10; j++)
			//		neuralNetworks.addAll(EvolutionEngine.spawn(neuralNetworks.get(i), neuralNetworks.get(j)));
			for(int i = 0; i < 100; i++)
				for(int j = 0; j < 10; j++)
					neuralNetworks.add(neuralNetworks.get(i).mutation());
			
			System.out.println("Added all nns");

			for(int i = 0; i < neuralNetworks.size(); i++)
			{
				//System.out.println("Testing nn#" + i);
				int totalScore = 0;
				for(int j = 0; j < 100; j++)
				{
					Game game = new Game();
					while (!game.hasGameEnded())
					{
						double[] tmp = new double[game.getTiles().length];
						for(int k = 0; k < game.getTiles().length; k++)
							tmp[k] = game.getTiles()[k];
						game.move(neuralNetworks.get(i).calculate(new Matrix(tmp, 16)));
					}
					totalScore += game.getScore();
				}
				neuralNetworks.get(i).setAverageScore(totalScore / 100);
			}
			
			System.out.println("AverageScore:" + neuralNetworks.get(0).getAverageScore());
			java.util.Collections.sort(neuralNetworks, new Comparator<NeuralNetwork>()
			{
				@Override
				public int compare(NeuralNetwork o1, NeuralNetwork o2)
				{
					return o1.getAverageScore() > o2.getAverageScore() ? 1 : 0;
				}
			});
			neuralNetworks = neuralNetworks.subList(0, 100);
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
			neuralNetworks = (List<NeuralNetwork>) in.readObject();
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
		for(int i = 0; i < neuralNetworks.size(); i++)
			if(neuralNetworks.get(i).getAverageScore() > neuralNetworks.get(index).getAverageScore())
				index = i;
		neuralNetworks.get(index).save(path);
	}

	public List<NeuralNetwork> getNeuralNetwork()
	{
		return neuralNetworks;
	}
}
