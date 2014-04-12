package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.Map;

import org.junit.Test;

public class MapTest {
	
	//Just a partial test at the moment
	@Test
	public void shortestPathTest(){
		Point currentPoint = new Point(0,0);
		Point endPoint = new Point(3,3);
		Map map = new Map();
		List<Point> points = new ArrayList<Point>();
		points = map.shortestPath(currentPoint, endPoint);
		System.out.println(points.toString());
		assertTrue(points.contains(new Point(1,0)));
		assertTrue(points.contains(new Point(2,0)));
		assertTrue(points.contains(new Point(3,0)));
		assertTrue(points.contains(new Point(3,1)));
		assertTrue(points.contains(new Point(3,2)));
		assertTrue(points.contains(new Point(3,3)));
		assertTrue(points.size() == 6);
	}
}
