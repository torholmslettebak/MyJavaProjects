package myTowerDefence;

import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class Map 
{
//	This class contains the map information and the map logic
	public char[][] statusMap;
	
	public Map()
	{
		statusMap = new char[50][50];
		for (char[] row: statusMap)
		{
			Arrays.fill(row, ' ');
		}
	}
	
	public void setTower(int x, int y, char symbol)
	{
		statusMap[x][y] = symbol;
	}

}
