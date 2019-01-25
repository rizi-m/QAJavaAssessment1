package game;

public class Coordinate {
	private int x;
	private int y;
	
	public Coordinate(Coordinate c) {
		x = c.x;
		y = c.y;
	}
	
	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == this) {
			return true;
		}
		if (arg0 instanceof Coordinate) {
			Coordinate other = (Coordinate) arg0;
			return (x == other.x && y == other.y);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
