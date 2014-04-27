package shipVisuals;

import view.JetSprite;
import view.Sprite;
import view.JetSprite.JetSize;
import model.Ship;

/*
 * Controls visuals for the Scout
 */
public class ScoutVisual extends ShipVisual {

	private Sprite sprite;
	private JetSprite jet;

	public ScoutVisual(Ship ship) {
		super(ship);

		jet = new JetSprite(-38, -3, this, JetSize.H16);
		jet.setVisible(false);
		addChild(jet);

		sprite = new Sprite("scout.png");
		addChild(sprite);

		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());

	}

	public void playMoveAnimation() {
		jet.setVisible(true);
//		System.out.println("moving");
	}

	public void playIdleAnimation() {
		jet.setVisible(false);
//		System.out.println("idling");
	}

}
