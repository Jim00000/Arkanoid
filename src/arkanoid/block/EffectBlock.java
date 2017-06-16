package arkanoid.block;

import java.util.Random;

import arkanoid.Movable;
import arkanoid.block.effect.DoubleScore;
import arkanoid.block.effect.Effective;
import arkanoid.block.effect.MoreBall;
import arkanoid.block.effect.NarrowerBlock;
import arkanoid.block.effect.WiderBlock;

public class EffectBlock extends Block implements Movable {

	private Effective effect;

	public synchronized final Effective getEffect() {
		return effect;
	}

	public EffectBlock(Block block, ControlledBlock controller) {
		this(block.getBlock().getX(), block.getBlock().getY(), block.getBlock().getWidth(),
				block.getBlock().getHeight(), controller);
	}

	public EffectBlock(double x, double y, double width, double height, ControlledBlock controller) {
		super(x, y, width, height);
		// Give an effect randomly
		effect = giveEffectRandomly();
	}

	@Override
	public void update() {
		// Make the block move downward
		super.getBlock().setY(super.getBlock().getY() + 1.0);
	}

	private final Effective giveEffectRandomly() {
		Random rand = new Random();
		final int selector = rand.nextInt() % 4;
		/// TODO: Complete it
		switch (selector) {
		case 1:
			return new NarrowerBlock();
		case 2:
			return new MoreBall();
		case 3:
			return new DoubleScore();
		default:
			return new WiderBlock();
		}
	}

}
