package tk.valoeghese.biomeoverhaul.surface;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import tk.valoeghese.biomeoverhaul.shape.NormalTypeHeightmap;
import tk.valoeghese.worldcomet.api.noise.Noise;
import tk.valoeghese.worldcomet.api.noise.OctaveOpenSimplexNoise;
import tk.valoeghese.worldcomet.api.surface.Surface;

public class GenericSurface extends Surface {
	public GenericSurface(Identifier id) {
		super(id);
	}

	private Noise beachNoise = null;
	private long seedCache = 0;

	@Override
	public void replaceSurfaceBlocks(IWorld world, Chunk chunk, Random rand, int x, int z, double noise) {
		long seed = world.getSeed();
		if (this.beachNoise == null || seed != this.seedCache) {
			this.seedCache = seed;
			this.beachNoise = new OctaveOpenSimplexNoise(new Random(seed), 3, 300.0);
		}

		boolean mountain = NormalTypeHeightmap.INSTANCE.isMountain(x, z);

		int run = -1;

		BlockPos.Mutable pos = new BlockPos.Mutable();
		pos.set(x & 15, 0, z & 15);
		boolean water = false;

		this.topBlock = SNOW_BLOCK;
		this.underBlock = SNOW_BLOCK;
		this.underwaterBlock = STONE;

		final int beachHeightOffset = (int) (1.5 + 3 * this.beachNoise.sample(x, z));

		for (int y = 255; y >= 0; --y) {
			// normal setup
			pos.setY(y);
			++run;

			if (y < rand.nextInt(5)) { // add bedrock
				chunk.setBlockState(pos, BEDROCK, false);
				continue;
			}

			// update blocks
			switch (y) {
			case 210:
				this.topBlock = rand.nextInt(3) == 0 ? STONE : SNOW_BLOCK;
				this.underBlock = STONE;
			case 180:
				this.topBlock = rand.nextInt(4) == 0 ? COBBLESTONE : STONE;
				this.underBlock = this.topBlock;
				this.underwaterBlock = GRAVEL;
			case 148:
				if (mountain) {
					boolean bool = noise > 0.0;
					this.topBlock = bool ? COBBLESTONE : COARSE_DIRT;
					this.underBlock = bool ? STONE : DIRT;
				} else {
					this.topBlock = COARSE_DIRT;
					this.underBlock = DIRT;
				}
			case 130:
				if (mountain) {
					boolean bool = rand.nextInt(5) == 0;
					this.topBlock = bool ? COBBLESTONE : COARSE_DIRT;
					this.underBlock = bool ? STONE : DIRT;
				} else {
					this.topBlock = GRASS_BLOCK;
					this.underBlock = DIRT;
				}
			case 90:
				this.topBlock = GRASS_BLOCK;
				this.underBlock = DIRT;
				this.underwaterBlock = SAND;
			case 62:
				this.underwaterBlock = GRAVEL;
			default:
				break;
			}

			if (y == 63 + beachHeightOffset) {
				this.topBlock = SAND;
				this.underBlock = SAND;
			}

			// normal block setting
			BlockState currentState = chunk.getBlockState(pos);

			if (currentState == AIR) {
				water = false;
				run = -1;
			} else if (currentState == WATER) {
				water = true;
				run = -1;
			} else if (currentState == STONE) {
				if (run < 5) {
					if (water) {
						chunk.setBlockState(pos, this.underwaterBlock, false);
					} else {
						if (run == 0) {
							chunk.setBlockState(pos, this.topBlock, false);
						} else {
							chunk.setBlockState(pos, this.underBlock, false);
						}
					}
				}
			}
		}
	}

	private static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
	private static final BlockState COBBLESTONE = Blocks.COBBLESTONE.getDefaultState();
	private static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.getDefaultState();
	private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
}
