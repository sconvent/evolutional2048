package de.convent.evolutional2048.backend;

import de.convent.evolutional2048.util.Direction;

public class Game
{
	int[] tiles;

	public Game()
	{
		tiles = new int[16];
		for(int i = 0; i < 16; i++)
		{
			tiles[i] = 0;
		}
		addRandom();
		addRandom();
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

	public int move(Direction direction)
	{
		int score = 0;
		// merge
		if(direction == Direction.UP) // up
		{
			score += merge(0, 4, 8, 12);
			score += merge(1, 5, 9, 13);
			score += merge(2, 6, 10, 14);
			score += merge(3, 7, 11, 15);
		}
		else if(direction == Direction.RIGHT) // right
		{
			score += merge(3, 2, 1, 0);
			score += merge(7, 6, 5, 4);
			score += merge(11, 10, 9, 8);
			score += merge(15, 14, 13, 12);
		}
		else if(direction == Direction.DOWN) // down
		{
			score += merge(12, 8, 4, 0);
			score += merge(13, 9, 5, 1);
			score += merge(14, 10, 6, 2);
			score += merge(15, 11, 7, 3);
		}
		else if(direction == Direction.LEFT) // left
		{
			score += merge(0, 1, 2, 3);
			score += merge(4, 5, 6, 7);
			score += merge(8, 9, 10, 11);
			score += merge(12, 13, 14, 15);
		}

		addRandom(); //TODO: fix: only addRandom when something changed
		return score;
	}

	private int merge(int i0, int i1, int i2, int i3)
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
		
		int score = 0;		
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
		return score;
	}
}