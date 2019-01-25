package game;


public class Cell {
	
	private Coordinate cellCoordinate;

	
	public Cell(int x, int y) {
		cellCoordinate = new Coordinate(x, y);
	}
	
	public Coordinate getCellCoordinate() {
		return cellCoordinate;
	}
	
	public Cell shiftSouth() {
		int oldY = cellCoordinate.getY();
		cellCoordinate.setY(oldY - 1);
		return this;
	}
	
	public Cell shiftNorth() {
		int oldY = cellCoordinate.getY();
		cellCoordinate.setY(oldY + 1);
		return this;
	}
	
	public Cell shiftEast() {
		int oldX = cellCoordinate.getX();
		cellCoordinate.setX(oldX - 1);
		return this;
	}
	
	public Cell shiftWest() {
		int oldX = cellCoordinate.getX();
		cellCoordinate.setX(oldX + 1);
		return this;
	}
	
	@Override
	public String toString() {
		return cellCoordinate.toString();
	}
	
}
