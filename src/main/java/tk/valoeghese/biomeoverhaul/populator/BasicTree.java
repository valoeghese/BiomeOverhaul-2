package tk.valoeghese.biomeoverhaul.populator;

import java.util.Random;

import net.minecraft.block.FluidBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import tk.valoeghese.worldcomet.api.populator.SurfacePopulator;
import tk.valoeghese.worldcomet.api.surface.SurfaceProvider;

public abstract class BasicTree extends SurfacePopulator {
	@Override
	protected void populateChunk(IWorld world, Random rand, int chunkX, int chunkZ, SurfaceProvider surfaceProvider, long seed) {
		this.heightSample = world.getChunk(chunkX, chunkZ).sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, 0, 0);
		super.populateChunk(world, rand, chunkX, chunkZ, surfaceProvider, seed);
	}

	@Override
	protected boolean generate(IWorld world, Random rand, SurfaceProvider surfaceProvider, int x, int y, int z) {
		BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z);

		if (world.getBlockState(pos.down()).getBlock() instanceof FluidBlock) {
			return false;
		}

		int height = this.calculateHeight(world, rand, x, z);

		if (this.canGenerate(world, x, y, z, height)) {
			return this.generate(world, rand, surfaceProvider, x, y, z, height);
		} else {
			return false;
		}
	}

	protected abstract int calculateHeight(BlockView world, Random rand, int x, int z);
	protected abstract boolean canGenerate(BlockView world, int x, int y, int z, int height);
	protected abstract boolean generate(IWorld world, Random rand, SurfaceProvider surfaceProvider, int x, int y, int z, int height);

	protected int heightSample = 63;
}
