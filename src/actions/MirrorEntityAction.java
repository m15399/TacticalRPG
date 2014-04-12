package actions;

import model.Entity;
import model.Observer;

/*
 * An Action that just mirrors an Entity
 */
public class MirrorEntityAction extends Action {

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
