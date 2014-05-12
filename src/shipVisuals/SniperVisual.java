package shipVisuals;

import utils.Position;
import view.JetSprite;
import view.JetSprite.JetSize;
import view.SniperBeam;
import view.SniperRing;
import model.Ship;

public class SniperVisual extends ShipVisual {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5443254295919243730L;

	private JetSprite jet;
	private SniperRing ring1, ring2;
	private double sinVar;
	
	private double ex, ey;
	
	
	public SniperVisual(Ship ship) {
		super(ship);
		
		sinVar = Math.random()*100;
		
		ring1 = new SniperRing(3, this);
		addChild(ring1);
		ring2 = new SniperRing(2.75, this);
		addChild(ring2);
		
		jet = new JetSprite(-25, 0, this, JetSize.H8);
		jet.setVisible(false);
		addChildBelowSprite(jet);
		
		getSprite().getPosition().setScale(.5, .5);
		
	}
	
	public void chargeRings(){
		ring1.brighten();
		ring2.brighten();
	}
	
	public void dimRings(){
		ring1.dim();
		ring2.dim();
	}
	
	public void update(){
		sinVar += .015;
		double offset = Math.sin(sinVar) * 6;
		double ox = -6;
		
		ring1.getPosition().setLocation(offset+ox, 0);
		ring2.getPosition().setLocation(-offset+ox, 0);
		
	}
	
	public void setTarget(Position enemyPos){
		this.ex = enemyPos.getX();
		this.ey = enemyPos.getY();
	}
	
	public void playAttackAnimation(int time){
		double x = getPosition().getX();
		double y = getPosition().getY();
		double dx = ex - x;
		double dy = ey - y;
		double length = Math.sqrt(dx * dx + dy * dy);
		
		double ox = 10;
		double oy = 0;
		
		double vx = getPosition().getX() + (getPosition().getMirrored() ? -ox : ox);
		double vy = getPosition().getY() + oy;
		
		double angle = utils.Utilities.angleBetween(vx , vy, ex, ey);
		getShip().getLevel().getCamera().addChild(new SniperBeam(ox, oy, angle, length, this));
	}

	public void playMoveAnimation(){
		jet.setVisible(true);
	}
	
	public void playIdleAnimation(){
		jet.setVisible(false);
	}
	
}
