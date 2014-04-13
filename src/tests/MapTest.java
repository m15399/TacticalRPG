package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Map;

import org.junit.Test;

import utils.Direction;

public class MapTest {
	
	//Just a partial test at the moment
	@Test
	public void shortestPathTest(){
		Point currentPoint = new Point(0,0);
		Point endPoint = new Point(3,3);
		Map map = new Map(10, 10);
		List<Direction> points = new ArrayList<Direction>();
		points = map.shortestPath(currentPoint, endPoint);
		System.out.println(points.toString());
		assertTrue(points.contains(Direction.DOWN));
		assertTrue(points.contains(Direction.RIGHT));
		assertFalse(points.contains(Direction.LEFT));
		assertFalse(points.contains(Direction.UP));
		assertTrue(points.size() == 6);
	}
}
