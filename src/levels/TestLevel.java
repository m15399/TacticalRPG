package levels;

import java.awt.Point;

import specific_ships_items.Bomber;
import specific_ships_items.Fighter;
import specific_ships_items.Scout;
import specific_ships_items.ScrapMetal;
import model.Level;

public class TestLevel extends Level {

	public TestLevel() {
		super(16, 12);
		
		// Ship Testing
		Scout scout = new Scout(new Point(2, 2));
		scout.updateHull(-scout.getHull()+1);
		scout.addToItems(new ScrapMetal());
		addShipToMap(scout);

		Bomber bomber = new Bomber(new Point(4, 4));
		bomber.updateHull(-bomber.getHull()+1);
		addEnemyShipToMap(bomber);
		addShipToMap(new Fighter(new Point(4, 2)));
		
		startTurn(0);
	}
	
	public void update(){
		super.update();
		
		// check objective
		if(getShips(0).size() == 0){
			System.out.println("\n\n\nYou lose!!");
			System.exit(0);
		}
		if(getShips(1).size() == 0){
			System.out.println("\n\n\nYou win!!");
			System.exit(0);
		}
	}

}
