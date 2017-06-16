package arkanoid.scene;

import java.util.Set;

import arkanoid.GameScene;
import arkanoid.block.Block;
import arkanoid.block.ColoredBlock;

import javafx.scene.paint.Color;

public class LeveLJava extends GameScene {

	@Override
	protected void setUpBlock(Set<Block> blocks) {
		blocks.add(new ColoredBlock(320,STATUSHEIGHT,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 15,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 30,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 45,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 60,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 75,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 90,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 105,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 120,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 135,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 150,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));

		blocks.add(new ColoredBlock(400,STATUSHEIGHT + 60,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(400,STATUSHEIGHT + 75,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(400,STATUSHEIGHT + 90,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 105,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 120,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 135,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 150,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 165,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 180,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		
		blocks.add(new ColoredBlock(400,STATUSHEIGHT + 165,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT + 180,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT + 195,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT + 210,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT + 225,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT + 240,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(400,STATUSHEIGHT + 255,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		
		blocks.add(new ColoredBlock(80,STATUSHEIGHT + 195,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 210,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 210,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 210,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 240,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 240,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 240,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));	
		
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 270,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 270,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 270,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));		

		blocks.add(new ColoredBlock(0,STATUSHEIGHT + 300,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(80,STATUSHEIGHT + 315,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 315,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 315,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 315,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(400,STATUSHEIGHT + 315,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT + 300,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		
		blocks.add(new ColoredBlock(80,STATUSHEIGHT + 345,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 345,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 345,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 345,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(400,STATUSHEIGHT + 345,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT + 330,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
	}
}
