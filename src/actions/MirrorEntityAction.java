package actions;

import utils.Observer;
import model.Entity;

/*
 * An Action that just mirrors an Entity
 */
public class MirrorEntityAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5704410929881124150L;
	Entity entity;
	
	public MirrorEntityAction(Entity e, Observer o){
		super(o);
		entity = e;
	}
	
	public void start(){
		entity.getPosition().mirror();
		finish();
	}
	
}
