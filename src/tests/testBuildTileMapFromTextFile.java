package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.BuildTileMapFromTextFile;

public class testBuildTileMapFromTextFile {
	
	@Test
	public void testBuildTileMapFromTextFile1(){
		String fileName = "testTileMap";
		BuildTileMapFromTextFile builder = new BuildTileMapFromTextFile(fileName);
		System.out.println(builder.toString()); //(Help for debugging in future)
		assertTrue(builder.toString().equals("00F00000\n00F00000\n0BBPp000\n000pp000\n000000S0\nAA00000M\n"));
	}
}
