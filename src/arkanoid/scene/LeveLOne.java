package arkanoid.scene;

import java.util.Set;

import arkanoid.GameScene;
import arkanoid.block.Block;
import arkanoid.block.ColoredBlock;

import javafx.scene.paint.Color;

public class LeveLOne extends GameScene {

	@Override
	protected void setUpBlock(Set<Block> blocks) {
		blocks.add(new ColoredBlock(0,STATUSHEIGHT,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		blocks.add(new ColoredBlock(80,STATUSHEIGHT,BLOCKWIDTH,BLOCKHEIGHT,Color.ORANGE,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT,BLOCKWIDTH,BLOCKHEIGHT,Color.YELLOW,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT,BLOCKWIDTH,BLOCKHEIGHT,Color.GREEN,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(400,STATUSHEIGHT,BLOCKWIDTH,BLOCKHEIGHT,Color.AQUA,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT,BLOCKWIDTH,BLOCKHEIGHT,Color.PURPLE,soundManager));
		blocks.add(new ColoredBlock(0,STATUSHEIGHT + 15,BLOCKWIDTH,BLOCKHEIGHT,Color.PURPLE,soundManager));
		blocks.add(new ColoredBlock(80,STATUSHEIGHT + 15,BLOCKWIDTH,BLOCKHEIGHT,Color.AQUA,soundManager));
		blocks.add(new ColoredBlock(160,STATUSHEIGHT + 15,BLOCKWIDTH,BLOCKHEIGHT,Color.BLUE,soundManager));
		blocks.add(new ColoredBlock(240,STATUSHEIGHT + 15,BLOCKWIDTH,BLOCKHEIGHT,Color.GREEN,soundManager));
		blocks.add(new ColoredBlock(320,STATUSHEIGHT + 15,BLOCKWIDTH,BLOCKHEIGHT,Color.YELLOW,soundManager));
		blocks.add(new ColoredBlock(400,STATUSHEIGHT + 15,BLOCKWIDTH,BLOCKHEIGHT,Color.ORANGE,soundManager));
		blocks.add(new ColoredBlock(480,STATUSHEIGHT + 15,BLOCKWIDTH,BLOCKHEIGHT,Color.RED,soundManager));
		
		
	}

}
