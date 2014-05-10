package model;

public abstract class Ability extends Castable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3051887460432166020L;
	private int cooldown, cooldownLeft;
	
	public Ability(String name, String description, Castable.TargetType targetType,
			int castRange, int cooldown) {
		super(name, description, targetType, castRange);
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
