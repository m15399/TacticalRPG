package model;

public class MagneticShield extends Item{
	private double shieldingBoost ;
	public MagneticShield() {
		shieldingBoost = 1.15;
	}

	@Override
	public void useOn(Ship ship) {
		ship.updateShielding(ship.getShielding()*shieldingBoost);
		
	}

}
