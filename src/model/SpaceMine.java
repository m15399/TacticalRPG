package model;

public class SpaceMine extends Item{
	private int damage;
	public SpaceMine() {
		damage = 100;
	}

	@Override
	public void useOn(Ship ship) {
		ship.updateHull(-damage);
	}
}
