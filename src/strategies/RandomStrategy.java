package strategies;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import model.Level;
import model.Ship;

public class RandomStrategy implements Strategy{
	public void doNextAction(Ship ship, Level level){		
		Random random = new Random();
		
		List<Ship> targets = level.getPossibleTargetsForAI(ship);
		
		if(targets.size() == 0){
			List<Point> potentialMoves = level.getPossibleMovesForAI(ship);
			
			while(potentialMoves.size() > 0){
				int randomMove = random.nextInt(potentialMoves.size());
				if(randomMove == ship.getMoves() || potentialMoves.size() == 1){
					level.moveShipTo(ship, potentialMoves.get(randomMove).x, potentialMoves.get(randomMove).y);
					break;
				}
				potentialMoves.remove(randomMove);
			}
		}
		else if(targets.size() >= 1){
			int randomShip = random.nextInt(targets.size());
			level.attackShip(ship, targets.get(randomShip));
		}
		else{
			System.out.println("Something went wrong selecting a move.");
		}
	}
}
