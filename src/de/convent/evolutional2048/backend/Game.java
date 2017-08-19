package de.convent.evolutional2048.backend;

public class Game
{
	int[] tiles;
	int nonZeroTiles;

	public Game()
	{
		tiles = new int[16];
		for(int i = 0; i < 16; i++)
		{
			tiles[i] = 0;
		}
	}

	public boolean hasGameEnded()
	{
		if(nonZeroTiles == 16)
		{
			// TODO: fix
			return true;
		}
		else
			return false;
	}

	public int move(int direction)
	{
		// merge
		if(direction == 0) // up
		{
			merge(0, 4, 8, 12);
			merge(1, 5, 9, 13);
			merge(2, 6, 10, 14);
			merge(3, 7, 11, 15);
		}
		else if(direction == 1) // right
		{
			merge(3, 2, 1, 0);
			merge(7, 6, 5, 4);
			merge(11, 10, 9, 8);
			merge(15, 14, 13, 12);
		}
		else if(direction == 1) // down
		{
			merge(12, 8, 4, 0);
			merge(13, 9, 5, 1);
			merge(14, 10, 6, 2);
			merge(15, 11, 7, 3);
		}
		else if(direction == 1) // left
		{
			merge(0, 1, 2, 3);
			merge(4, 5, 6, 7);
			merge(8, 9, 10, 11);
			merge(12, 13, 14, 15);
		}

		// fill
		return 0;

	}

	private int merge(int i0, int i1, int i2, int i3)
	{
		if(tiles[i0] == tiles[i1] && tiles[i2] == tiles[i3])
		{
			tiles[i0] = 2 * tiles[i0];
			tiles[i1] = 2 * tiles[i2];
			tiles[i2] = 0;
			tiles[i3] = 0;
			return 2;
		}
		if(tiles[i0] == tiles[i1])
		{
			tiles[i0] = 2 * tiles[i0];
			tiles[i1] = tiles[i2];
			tiles[i2] = tiles[i3];
			tiles[i3] = 0;
			return 1;
		}
		if(tiles[i1] == tiles[i2])
		{
			tiles[i1] = 2 * tiles[i1];
			tiles[i2] = tiles[i3];
			tiles[i3] = 0;
			return 1;
		}
		if(tiles[i2] == tiles[i3])
		{
			tiles[i2] = 2 * tiles[i2];
			return 1;
		}
		return 0;
	}
}