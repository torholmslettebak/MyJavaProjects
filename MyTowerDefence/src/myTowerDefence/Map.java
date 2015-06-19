package myTowerDefence;

import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class Map 
{
//	This class contains the map information and the map logic
	public char[][] statusMap;
	private int xLength;
	private int yLength;
	
	public Map(int tileSize, int width, int height)
	{
		xLength = width/tileSize;
		yLength = height/tileSize;
		statusMap = new char[xLength][yLength];
		for (char[] row: statusMap)
		{
			Arrays.fill(row, ' ');
		}
		Arrays.fill(statusMap[1], 't');

	}
	
	public void setTower(int x, int y, char symbol)
	{
		statusMap[x][y] = symbol;
	}
	
	public char getTile(int x, int y)
	{
		return statusMap[x][y];
	}
	
	public int getLengthX()
	{
		return xLength;
	}
	
	public int getLengthY()
	{
		return yLength;
	}

}
