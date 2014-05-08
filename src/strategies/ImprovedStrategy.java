package strategies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import specific_ships_items.AISpawner;

import model.Level;
import model.Ship;

public class ImprovedStrategy implements Strategy {

	@Override
	public void doNextAction(Ship ship, Level level) {
		List<Ship> targets = level.getPossibleTargetsForAI(ship);
		List<Point> potentialMoves = level.getPossibleMovesForAI(ship);
		List<Ship> allEnemyShips = level.getTargettableShips(0);

		if (allEnemyShips.size() == 0) {

			level.waitShip(ship);

		} else if (targets.size() >= 1) {
			Ship leastHealthShip = findShipWithLowestHealth(targets);
			level.attackShip(ship, leastHealthShip);
			ship.setCanAttack(false);
			ship.setCanMove(false);

		} else if (ship.getCanMove() && potentialMoves.size() > 0) {

			Point move = findClosestShipPointToMoveTo(allEnemyShips,
					potentialMoves);

			level.moveShipTo(ship, move.x, move.y);
			ship.setCanMove(false);

		} else if (ship instanceof AISpawner && ship.getCanUseAbility()
				&& ship.getAbility().getCooldownLeft() == 0) {
			// spawn ship
			level.useEnemyAbility(ship);

		} else {
			level.waitShip(ship);
		}

	}

	private Ship findShipWithLowestHealth(List<Ship> targets) {
		Ship leastHealthShip = targets.get(0);
		for (int i = 0; i < targets.size(); i++) {
			if (targets.get(i).getHull() <= leastHealthShip.getHull()) {
				leastHealthShip = targets.get(i);
			}
		}
		return leastHealthShip;
	}

	private Point findClosestShipPointToMoveTo(List<Ship> allEnemyShips,
			List<Point> potentialMoves) {

		Ship closestShip = allEnemyShips.get(0);
		double closestDistance = 10000;
		for (Ship s : allEnemyShips) {
			double minDistance = this.getClosestDistance(s, potentialMoves);
			if (minDistance <= closestDistance) {
				closestShip = s;
				closestDistance = minDistance;
			}
		}
		Point moveTo = closestMovablePoint(closestShip, potentialMoves);
		return moveTo;
	}

	private Point closestMovablePoint(Ship s, List<Point> potentialMoves) {
		Point closestMovablePoint = potentialMoves.get(3);
		double shortestDistance = 10000;
		for (Point p : potentialMoves) {
			double distance = this.getDistance(s.getLocation(), p);
			if (distance <= shortestDistance) {
				closestMovablePoint = p;
				shortestDistance = distance;
			}
		}
		return closestMovablePoint;
	}

	private double getDistance(Point point1, Point point2) {
		double x1 = point1.getX();
		double x2 = point2.getX();
		double y1 = point1.getY();
		double y2 = point2.getY();

		double distance = Math.sqrt(Math.pow((x2 - x1), 2)
				+ Math.pow((y2 - y1), 2));
		return distance;
	}

	private double getClosestDistance(Ship ship, List<Point> pointsAI) {
		double minDistance = 100000;
		for (Point p : pointsAI) {
			double distance = this.getDistance(ship.getLocation(), p);
			if (distance < minDistance) {
				minDistance = distance;
			}
		}
		return minDistance;
	}
}
