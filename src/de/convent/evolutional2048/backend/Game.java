package de.convent.evolutional2048.backend;

import de.convent.evolutional2048.util.Direction;

public class Game
{
	int[] tiles;
	int score;

	public Game()
	{
		tiles = new int[16];
		for(int i = 0; i < 16; i++)
		{
			tiles[i] = 0;
		}
		addRandom();
		addRandom();
		score = 0;
	}

	private void addRandom()
	{
		int count = 0;
		for(int i = 0; i < 16; i++)
		{
			if(tiles[i] == 0)
				count++;
		}
		int index = (int) (Math.random() * count);
		index++;
		count = 0;
		for(int i = 0; i < 16; i++)
		{
			if(tiles[i] == 0)
				count++;
			if(count == index)
			{
				tiles[i] = Math.random() > 0.9 ? 4 : 2;
				break;
			}
		}
	}

	public int[] getTiles()
	{
		return tiles;
	}
	
	public int getScore()
	{
		return score;
	}

	public boolean hasGameEnded()
	{
		int count = 0;
		for(int i = 0; i < 16; i++)
		{
			if(tiles[i] != 0)
				count++;
		}
		
		if(count == 16)
			return true; // TODO: fix
		else
			return false;
	}

	public void move(Direction direction)
	{
		// merge
		if(direction == Direction.UP) // up
		{
			merge(0, 4, 8, 12);
			merge(1, 5, 9, 13);
			merge(2, 6, 10, 14);
			merge(3, 7, 11, 15);
		}
		else if(direction == Direction.RIGHT) // right
		{
			merge(3, 2, 1, 0);
			merge(7, 6, 5, 4);
			merge(11, 10, 9, 8);
			merge(15, 14, 13, 12);
		}
		else if(direction == Direction.DOWN) // down
		{
			merge(12, 8, 4, 0);
			merge(13, 9, 5, 1);
			merge(14, 10, 6, 2);
			merge(15, 11, 7, 3);
		}
		else if(direction == Direction.LEFT) // left
		{
			merge(0, 1, 2, 3);
			merge(4, 5, 6, 7);
			merge(8, 9, 10, 11);
			merge(12, 13, 14, 15);
		}

		addRandom(); //TODO: fix: only addRandom when something changed
	}

	private void merge(int i0, int i1, int i2, int i3)
	{
		if(tiles[i2] == 0)
		{
			tiles[i2] = tiles[i3];
			tiles[i3] = 0;
		}
		if(tiles[i1] == 0)
		{
			tiles[i1] = tiles[i2];
			tiles[i2] = tiles[i3];
			tiles[i3] = 0;
		}
		if(tiles[i0] == 0)
		{
			tiles[i0] = tiles[i1];
			tiles[i1] = tiles[i2];
			tiles[i2] = tiles[i3];
			tiles[i3] = 0;
		}
		
		if(tiles[i0] == tiles[i1] && tiles[i2] == tiles[i3] && tiles[i0] != 0  && tiles[i2] != 0)
		{
			tiles[i0] = 2 * tiles[i0];
			score += tiles[i0];
			tiles[i1] = 2 * tiles[i2];
			score += tiles[i1];
			tiles[i2] = 0;
			tiles[i3] = 0;
		}
		else if(tiles[i0] == tiles[i1] && tiles[i0] != 0)
		{
			tiles[i0] = 2 * tiles[i0];
			score += tiles[i0];
			tiles[i1] = tiles[i2];
			tiles[i2] = tiles[i3];
			tiles[i3] = 0;
		}
		else if(tiles[i1] == tiles[i2] && tiles[i1] != 0)
		{
			tiles[i1] = 2 * tiles[i1];
			score += tiles[i1];
			tiles[i2] = tiles[i3];
			tiles[i3] = 0;
		}
		else if(tiles[i2] == tiles[i3] && tiles[i2] != 0)
		{
			tiles[i2] = 2 * tiles[i2];
			score += tiles[i2];
		}
	}
}