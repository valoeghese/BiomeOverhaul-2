package tk.valoeghese.biomeoverhaul.populator.normal;

import java.util.Random;

import net.minecraft.world.IWorld;
import tk.valoeghese.biomeoverhaul.populator.BasicTree;
import tk.valoeghese.worldcomet.api.populator.SurfacePopulator;
import tk.valoeghese.worldcomet.api.surface.SurfaceProvider;

public class PineTree extends BasicTree {
	@Override
	protected int getCount(Random rand, SurfaceProvider surfaceProvider, int chunkX, int chunkZ) {
		double temperature = NormalPopulatorMaths.INSTANCE.getTemperature(chunkX, chunkZ);

		if (temperature > 0.6) {
			double humidity 
			int countModifier = NormalPopulatorMaths.INSTANCE.getCountModifier(surfaceProvider.getSurface(chunkX << 4, chunkZ << 4, this.heightSample));
		}
	}

	@Override
	protected boolean generate(IWorld world, Random rand, SurfaceProvider surfaceProvider, int x, int y, int z) {
		// TODO Auto-generated method stub
		return false;
	}
}
