package levels;

import java.awt.Point;

import specificic_ships_items.Bomber;
import specificic_ships_items.Fighter;
import specificic_ships_items.Scout;
import model.Level;

public class TestLevel extends Level {

	public TestLevel() {
		super(16, 12);
		
		// Ship Testing
		Scout scout = new Scout(new Point(2, 2));
		scout.updateHull(-30);
		addShipToMap(scout);

		Bomber bomber = new Bomber(new Point(3, 0));
		bomber.setTeam(1);
		addShipToMap(bomber);
		addShipToMap(new Fighter(new Point(4, 2)));
	}

}
