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
		Point endPoint = new Point(2,3);
		Map map = new Map(3, 4);
		List<Direction> points = new ArrayList<Direction>();
		points = map.shortestPath(currentPoint, endPoint);
		for(Direction dir: points){
			System.out.println(dir.toString());
		}
		assertTrue(points.contains(Direction.DOWN));
		assertTrue(points.contains(Direction.RIGHT));
		assertFalse(points.contains(Direction.LEFT));
		assertFalse(points.contains(Direction.UP));
		assertTrue(points.size() == 5);
	}
	
	@Test
	public void shortestPathTest2(){
		Point currentPoint = new Point(3,3);
		Point endPoint = new Point(0,0);
		Map map = new Map(4, 4);
		List<Direction> points = new ArrayList<Direction>();
		points = map.shortestPath(currentPoint, endPoint);
		for(Direction dir: points){
			System.out.println(dir.toString());
		}
		assertFalse(points.contains(Direction.DOWN));
		assertFalse(points.contains(Direction.RIGHT));
		assertTrue(points.contains(Direction.LEFT));
		assertTrue(points.contains(Direction.UP));
		assertTrue(points.size() == 6);
	}
	
	@Test
	public void shortestPathTest3(){
		Point currentPoint = new Point(3,3);
		Point endPoint = new Point(0,3);
		Map map = new Map(4, 4);
		List<Direction> points = new ArrayList<Direction>();
		points = map.shortestPath(currentPoint, endPoint);
		for(Direction dir: points){
			System.out.println(dir.toString());
		}
		assertFalse(points.contains(Direction.DOWN));
		assertFalse(points.contains(Direction.RIGHT));
		assertTrue(points.contains(Direction.LEFT));
		assertFalse(points.contains(Direction.UP));
		assertTrue(points.size() == 3);
	}
	
	@Test
	public void shortestPathTest4(){
		Point currentPoint = new Point(3,3);
		Point endPoint = new Point(5,3);
		Map map = new Map(16, 12);
		List<Direction> points = new ArrayList<Direction>();
		points = map.shortestPath(currentPoint, endPoint);
		for(Direction dir: points){
			System.out.println(dir.toString());
		}
		assertFalse(points.contains(Direction.DOWN));
		assertTrue(points.contains(Direction.RIGHT));
		assertFalse(points.contains(Direction.LEFT));
		assertFalse(points.contains(Direction.UP));
		assertTrue(points.size() == 2);
	}
	
	@Test
	public void shortestPathTest5(){
		Point currentPoint = new Point(2,2);
		Point endPoint = new Point(5,2);
		Map map = new Map(16, 12);
		List<Direction> points = new ArrayList<Direction>();
		points = map.shortestPath(currentPoint, endPoint);
		for(Direction dir: points){
			System.out.println(dir.toString());
		}
		assertFalse(points.contains(Direction.DOWN));
		assertTrue(points.contains(Direction.RIGHT));
		assertFalse(points.contains(Direction.LEFT));
		assertFalse(points.contains(Direction.UP));
		assertTrue(points.size() == 3);
	}
}
