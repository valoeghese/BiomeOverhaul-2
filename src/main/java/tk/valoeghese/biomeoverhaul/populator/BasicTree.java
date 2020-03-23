package tk.valoeghese.biomeoverhaul.populator;

import java.util.Random;

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

	protected int heightSample = 63;
}
