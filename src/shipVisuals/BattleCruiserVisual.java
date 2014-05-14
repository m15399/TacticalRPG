package shipVisuals;

import view.JetSprite;
import view.JetSprite.JetSize;
import model.Ship;

public class BattleCruiserVisual extends ShipVisual {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3368080337604897682L;
	
	private JetSprite jet;
	
	
	public BattleCruiserVisual(Ship ship) {
		super(ship);

		jet = new JetSprite(-38, -3, this, JetSize.H16);
		jet.setVisible(false);
		addChildBelowSprite(jet);

	}

	public void playMoveAnimation() {
		jet.setVisible(true);
	}

	public void playAttackAnimation(int time) {


	}

	public void playIdleAnimation() {
		jet.setVisible(false);
	}
	
}
