package model;

public abstract class Ability extends Castable {

	private int cooldown, cooldownLeft;
	
	public Ability(String name, String description, boolean isTargetted,
			int castRange, int cooldown) {
		super(name, description, isTargetted, castRange);
		this.cooldown = cooldown;
		cooldownLeft = 0;
		
	}

	public void startTurn(){
		if(cooldownLeft > 0)
			cooldownLeft--;
	}
	
	public void resetCooldown(){
		cooldownLeft = cooldown;
	}
	
	public int getCooldownLeft(){
		return cooldownLeft;
	}
	
	public int getCooldown(){
		return cooldown;
	}
	
	
}
