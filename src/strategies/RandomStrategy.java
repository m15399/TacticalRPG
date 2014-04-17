package strategies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Level;
import model.Ship;

public class RandomStrategy implements Strategy{
	public void doNextAction(Ship ship, Level level){
		Random random = new Random();
		List<Ship> targets = new ArrayList<Ship>();
		targets = level.getMap().shipsWithinRange(ship.getLocation(), ship.getRange());
		if(targets == null || targets.size() == 0){
			List<Point> potentialMoves = level.getMap().listPossibleMoves(ship);
			while(potentialMoves.size() > 0){
				int randomMove = random.nextInt(potentialMoves.size());
				if(randomMove == ship.getMoves() || potentialMoves.size() == 1){
					level.moveSelectedShipTo(potentialMoves.get(randomMove).x, potentialMoves.get(randomMove).y);
				}
				potentialMoves.remove(randomMove);
			}
		}
		else if(targets.size() == 1){
			ship.attack(targets.get(0));
		}
		else if(targets.size() > 1){
			int randomShip = random.nextInt(targets.size());
			ship.attack(targets.get(randomShip));
		}
		else{
			System.out.println("Something went wrong selecting a move.");
		}
	}
}
