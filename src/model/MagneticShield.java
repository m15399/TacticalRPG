package model;

public class MagneticShield extends Item{
	private double armorBoost;
	public MagneticShield() {
		armorBoost = 1.15;
	}

	@Override
	public void useOn(Ship ship) {
		ship.setArmor(ship.getArmor()*armorBoost);
		
	}

}
