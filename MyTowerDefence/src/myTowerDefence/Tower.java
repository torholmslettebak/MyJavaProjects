package myTowerDefence;

public class Tower 
{
	private float damage;
	private int range;
	private float accuracy;
	private float frequenzy;
	private int level;
	private int cost;
	private char mapSymbol;
	
	public float getDamage()
	{
		return damage;
	}
	
	public void setDamage(float damage)
	{
		this.damage = damage;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public float getFrequenzy() {
		return frequenzy;
	}

	public void setFrequenzy(float frequenzy) {
		this.frequenzy = frequenzy;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public char getMapSymbol() {
		return mapSymbol;
	}

	public void setMapSymbol(char mapSymbol) {
		this.mapSymbol = mapSymbol;
	}
}
