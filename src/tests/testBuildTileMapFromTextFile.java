package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.BuildTileMapFromTextFile;
import model.Tile;

public class testBuildTileMapFromTextFile {
	
	@Test
	public void testBuildTileMapFromTextFile(){
		String fileName = "testTileMap";
		BuildTileMapFromTextFile builder = new BuildTileMapFromTextFile(fileName);
		Tile[][] tiles = builder.getTiles();
		//System.out.println(builder.toString()); (Help for debugging in future)
		assertTrue(builder.toString().equals("EEEEEEEE\nE000000E\nEBBTT00E\nE00TT00E\nE00000SE\nEEEEEEEE\n"));
	}
}
