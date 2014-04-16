package levels;

import java.awt.Point;

import model.Bomber;
import model.Fighter;
import model.Level;
import model.Scout;

public class TestLevel extends Level {

	public TestLevel() {
		
		// Ship Testing
		Scout scout = new Scout(new Point(2, 2));
		scout.updateHull(-30);
		addShipToMap(scout);

		Bomber bomber = new Bomber(new Point(4, 4));
		bomber.setTeam(1);
		addShipToMap(bomber);
		addShipToMap(new Fighter(new Point(4, 2)));
	}

}
